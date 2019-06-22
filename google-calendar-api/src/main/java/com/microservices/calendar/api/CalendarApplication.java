package com.microservices.calendar.api;

import com.microservices.calendar.api.configuration.InitConfiguration;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import rx.Single;
import java.util.NoSuchElementException;
import static com.microservices.calendar.api.controller.Errors.error;

public class CalendarApplication extends AbstractVerticle {

    @Override
    public void start() {
        // Create a router object.
        Router router = Router.router(vertx);
        // enable parsing of request bodies
        router.route().handler(BodyHandler.create());
        // implement a basic REST CRUD mapping
        router.post("/api/v1/event").handler(this::createEvent);
        // health check
        router.get("/health").handler(rc -> rc.response().end("OK"));
        // web interface
        router.get().handler(StaticHandler.create());

        InitConfiguration.init(vertx)
                .andThen(initHttpServer(router))
                .subscribe(
                        (http) -> System.out.println("Server ready on port " + http.actualPort()),
                        Throwable::printStackTrace
                );

    }

    private Single<HttpServer> initHttpServer(Router router) {
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
