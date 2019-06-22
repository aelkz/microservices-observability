package com.microservices.calendar.api;

import com.microservices.calendar.api.configuration.InitConfiguration;
import io.reactivex.Single;
import io.vertx.core.Launcher;
import io.vertx.core.json.JsonObject;
import java.util.NoSuchElementException;
import io.vertx.reactivex.config.ConfigRetriever;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.BodyHandler;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import io.vertx.reactivex.ext.web.RoutingContext;
import static com.microservices.calendar.api.controller.Errors.error;

public class CalendarApplication extends AbstractVerticle {

    public static void main(final String[] args) {
        Launcher.executeCommand("run", CalendarApplication.class.getName());
    }

    @Override
    public void start() {
        // Create a router object.
        Router router = Router.router(vertx);

        // Bind "/" to our hello message - so we are still compatible.
        router.route("/").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "text/html")
                .end("<h1>Google Calendar API Vert.x 3 application</h1>");
        });

        // enable parsing of request bodies
        router.route().handler(BodyHandler.create());
        // implement a basic REST CRUD mapping
        router.post("/api/v1/event").handler(this::createEvent);
        // health check
        router.get("/health").handler(rc -> rc.response().end("OK"));
        // web interface
        router.get().handler(StaticHandler.create());

        ConfigRetriever retriever = ConfigRetriever.create(vertx);
        // retriever.rxGetConfig()

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

    private void createEvent(RoutingContext ctx) {
        JsonObject item;

        String userApiKey = ctx.request().getHeader("Google-Calendar-API-User-Key");

        try {
            item = ctx.getBodyAsJson();
        } catch (RuntimeException e) {
            error(ctx, 415, "invalid payload");
            return;
        }

        if (item == null) {
            error(ctx, 415, "invalid payload");
            return;
        }

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
