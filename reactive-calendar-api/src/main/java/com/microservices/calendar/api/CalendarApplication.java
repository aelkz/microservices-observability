package com.microservices.calendar.api;

import com.microservices.calendar.api.adapter.MultiMapExtractAdapter;
import com.microservices.calendar.api.adapter.ReactiveMultiMapExtractAdapter;
import com.microservices.calendar.api.configuration.InitConfiguration;
import io.opentracing.Scope;
import io.opentracing.SpanContext;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import io.reactivex.Single;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonObject;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.CorsHandler;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import io.vertx.reactivex.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.uber.jaeger.Configuration;
import com.uber.jaeger.micrometer.MicrometerMetricsFactory;
import com.uber.jaeger.samplers.ConstSampler;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import static com.microservices.calendar.api.controller.Errors.error;
import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import static io.vertx.core.http.HttpMethod.*;

public class CalendarApplication extends AbstractVerticle {
    private static Logger logger = LoggerFactory.getLogger(CalendarApplication.class);

    private final String API_KEY = "Reactive-Calendar-API-User-Key";

    public Tracer appTracer;
    public SpanContext parentSpanContext;

    public static void main(final String[] args) {
        Launcher.executeCommand("run", CalendarApplication.class.getName());
    }

    @Override
    public void start() {
        // Create a router object.
        Router router = Router.router(vertx);

        CorsHandler cors = CorsHandler.create("*");
        cors.allowedHeader(CONTENT_TYPE.toString());
        cors.allowedMethod(POST);
        router.route().handler(cors);

        String jaegerHost = null;
        Integer jaegerPort = null;

        try {
            jaegerHost = System.getenv("JAEGER_HOST");
            jaegerPort = Integer.valueOf(System.getenv("JAEGER_PORT"));
        }catch (Exception e) {
            jaegerHost = "localhost";
            jaegerPort = 6831;
        }

        MicrometerMetricsFactory metricsFactory = new MicrometerMetricsFactory();

        Configuration configuration = new Configuration("jaeger-vertx-calendar");
        appTracer = configuration
            .withReporter(
                new Configuration.ReporterConfiguration()
                    .withLogSpans(true)
                    .withFlushInterval(1000)
                    .withMaxQueueSize(10000)
                    .withSender(
                        new Configuration.SenderConfiguration()
                            .withAgentHost(jaegerHost)
                            .withAgentPort(jaegerPort)
                    )
            )
            .withSampler(
                new Configuration.SamplerConfiguration()
                    .withType(ConstSampler.TYPE)
                    .withParam(1)
            )
            .getTracerBuilder()
            .withMetricsFactory(metricsFactory)
            .build();

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "text/html")
                .end("<h1>Reactive Calendar API Vert.x 3.6.3.redhat-00009 application</h1>");
        });

        PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
        Metrics.addRegistry(registry);

        startPrometheusHttpServer(registry);

        // enable parsing of request bodies
        router.route().handler(BodyHandler.create());
        // implement REST CRUD mapping
        router.post("/api/v1/event").handler(this::createEvent);
        // health check
        router.get("/health").handler(rc -> rc.response().end("OK"));
        // web interface
        router.get().handler(StaticHandler.create());

        ConfigStoreOptions store = new ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setConfig(new JsonObject().put("path", "conf/config.yaml")
            );

        ConfigRetriever retriever = ConfigRetriever.create(vertx,
            new ConfigRetrieverOptions().addStore(store));

        InitConfiguration.init(vertx)
            .andThen(startHttpServer(router))
            .subscribe(
                (http) -> System.out.println("Server ready on port " + http.actualPort()),
                Throwable::printStackTrace
            );
    }

    private void startPrometheusHttpServer(PrometheusMeterRegistry registry) {
        System.out.println("VertX Prometheus listening on port: 8081");

        vertx.createHttpServer()
            .requestHandler(req -> req.response().end(registry.scrape()))
            .listen(8081);
    }

    private Single<HttpServer> startHttpServer(Router router) {
        Integer vertxPort = null;

        try {
            vertxPort = Integer.valueOf(System.getenv("HTTP_PORT"));
        }catch (Exception e) {
            vertxPort = 8070;
        }

        System.out.println("VertX app listening on port:"+vertxPort);

        // Create the HTTP server and pass the "accept" method to the request handler.
        return vertx
            .createHttpServer()
            .requestHandler(router)
            .rxListen(vertxPort);
    }

    private void createEvent(RoutingContext ctx) {
        parentSpanContext = appTracer.extract(Format.Builtin.HTTP_HEADERS,
                new ReactiveMultiMapExtractAdapter(ctx.request().headers()));

        System.out.println("\n\n\n");
        System.out.println("REQUEST HEADERS");
        ctx.request().headers().entries().forEach((e) -> {
            Map.Entry entry = e;
            System.out.println(e.getKey() + ":" + e);
        });

        try (Scope scope = appTracer
                .buildSpan(ctx.request().method().toString())
                .asChildOf(parentSpanContext)
                .startActive(true)) {

            logger.info("VertX - checking request header");
            String userApiKey = ctx.request().getHeader(API_KEY);

            if (userApiKey == null) {
                logger.info("invalid header: " + API_KEY + " not found");
                error(ctx, 400, "invalid header: " + API_KEY + " not found");
                return;
            }

            logger.info("VertX - checking request body");
            JsonObject item;

            try {
                item = ctx.getBodyAsJson();
            } catch (RuntimeException e) {
                logger.info("invalid payload: expecting json");
                error(ctx, 415, "invalid payload: expecting json");
                return;
            }

            if (item == null) {
                logger.info("invalid payload: expecting json");
                error(ctx, 415, "invalid payload: expecting json");
                return;
            }

            logger.info("VertX - post new event");
            logger.info("start: " + item.getValue("startDate"));
            logger.info("end: " + item.getValue("endDate"));
            logger.info("calories: " + item.getInteger("calories"));

            item.put("synced", true);

            ctx.response()
                    .putHeader("Location", "/api/v1/event/" + item.getLong("id"))
                    .putHeader("Content-Type", "application/json")
                    .setStatusCode(201)
                    .end(item.encodePrettily());

            System.out.println("received new reactive-calendar event from user api-key=" + userApiKey);
        }
    }

    private void writeError(RoutingContext ctx, Throwable err) {
        if (err instanceof NoSuchElementException) {
            error(ctx, 404, err);
        } else if (err instanceof IllegalArgumentException) {
            error(ctx, 422, err);
        } else {
            error(ctx, 409, err);
        }
    }

    private String getEnv(String key, String defaultValue) {
        String s = System.getenv(key);
        if (s == null) {
            return defaultValue;
        }
        return s;
    }
}
