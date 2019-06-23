package com.microservices.calendar.api;

import com.microservices.calendar.api.configuration.InitConfiguration;
import io.reactivex.Single;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonObject;
import java.util.NoSuchElementException;
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
import static com.microservices.calendar.api.controller.Errors.error;
import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import static io.vertx.core.http.HttpMethod.*;

public class CalendarApplication extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(CalendarApplication.class);

    private final String API_KEY = "Google-Calendar-API-User-Key";

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


        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "text/html")
                .end("<h1>Google Calendar API Vert.x 3 application</h1>");
        });

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

//        retriever.rxGetConfig()
//            .doOnSuccess(config -> {
//                System.out.println(config.getInteger("HTTP_PORT", 8070));
//            })
//           .flatMapCompletable(config -> startHttpServer(config, router).toCompletable());

    }

    private Single<HttpServer> startHttpServer(Router router) {
        System.out.println("VertX app listening on port:"+config().getInteger("HTTP_PORT", 8070));

        // Create the HTTP server and pass the "accept" method to the request handler.
        return vertx
            .createHttpServer()
            .requestHandler(router)
            .rxListen(config().getInteger("HTTP_PORT", 8070));

    }

    private void checkRequestBody(RoutingContext ctx) {
        logger.info("VertX - checking request body");
        JsonObject item;

        try {
            item = ctx.getBodyAsJson();
        } catch (RuntimeException e) {
            error(ctx, 415, "invalid payload: expecting json");
            return;
        }

        if (item == null) {
            error(ctx, 415, "invalid payload: expecting json");
            return;
        }
    }

    private void checkHeaderApiKey(RoutingContext ctx) {
        logger.info("VertX - checking request header");
        String userApiKey = ctx.request().getHeader(API_KEY);

        if (userApiKey == null) {
            error(ctx, 400, "invalid header: "+API_KEY+" not found");
            return;

        }
    }

    private void createEvent(RoutingContext ctx) {
        checkHeaderApiKey(ctx);
        checkRequestBody(ctx);

        logger.info("VertX - post new event");
        JsonObject item;
        item = ctx.getBodyAsJson();

        String userApiKey = ctx.request().getHeader(API_KEY);

        logger.info("start: "+item.getValue("startDate"));
        logger.info("end: "+item.getValue("endDate"));
        logger.info("calories: "+item.getInteger("calories"));

        item.put("synced",true);

        ctx.response()
                .putHeader("Location", "/api/v1/event/" + item.getLong("id"))
                .putHeader("Content-Type", "application/json")
                .setStatusCode(201)
                .end(item.encodePrettily());

        System.out.println("received new google-calendar event from user api-key="+userApiKey);
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

    private String getEnv(String key, String dv) {
        String s = System.getenv(key);
        if (s == null) {
            return dv;
        }
        return s;
    }
}
