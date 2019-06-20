package com.microservices.calendar.api;

import com.microservices.calendar.api.configuration.InitConfiguration;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.core.http.HttpServerResponse;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Single;
import java.util.NoSuchElementException;
import static com.microservices.calendar.api.controller.Errors.error;

public class CalendarApplication extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(CalendarApplication.class);

    @Override
    public void start() {
        // Create a router object.
        Router router = Router.router(vertx);
        // enable parsing of request bodies
        router.route().handler(BodyHandler.create());
        // perform validation of the :id parameter
        router.route("/api/v1/event/:id").handler(this::validateId);
        // implement a basic REST CRUD mapping
        router.get("/api/v1/events").handler(this::retrieveAll);
        router.post("/api/v1/event").handler(this::createEvent);
        router.get("/api/v1/event/:id").handler(this::getOne);
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
        logger.info("VertX app listening on port:"+config().getInteger("HTTP_PORT", 8070));
        System.out.println("VertX app listening on port:"+config().getInteger("HTTP_PORT", 8070));

        // Create the HTTP server and pass the "accept" method to the request handler.
        return vertx
            .createHttpServer()
            .requestHandler(router)
            .rxListen(config().getInteger("HTTP_PORT", 8070));

    }

    private void validateId(RoutingContext ctx) {
        try {
            ctx.put("fruitId", Long.parseLong(ctx.pathParam("id")));
            // continue with the next handler in the route
            ctx.next();
        } catch (NumberFormatException e) {
            error(ctx, 400, "invalid id: " + e.getCause());
        }
    }

    private void retrieveAll(RoutingContext ctx) {
        HttpServerResponse response = ctx.response()
                .putHeader("Content-Type", "application/json");
        JsonArray res = new JsonArray();

        res.add(1L);
        response.end(res.encodePrettily());
    }


    private void getOne(RoutingContext ctx) {
        HttpServerResponse response = ctx.response()
                .putHeader("Content-Type", "application/json");

//        store.read(ctx.get("fruitId"))
//                .subscribe(
//                        json -> response.end(json.encodePrettily()),
//                        err -> {
//                            if (err instanceof NoSuchElementException) {
//                                error(ctx, 404, err);
//                            } else if (err instanceof IllegalArgumentException) {
//                                error(ctx, 415, err);
//                            } else {
//                                error(ctx, 500, err);
//                            }
//                        }
//                );
    }

    private void createEvent(RoutingContext ctx) {
        JsonObject item;

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

        ctx.response()
                .putHeader("Location", "/api/fruits/" + item.getLong("id"))
                .putHeader("Content-Type", "application/json")
                .setStatusCode(201)
                .end(item.encodePrettily());

        System.out.println("hello master");

//        store.create(item)
//                .subscribe(
//                        json ->
//                                ctx.response()
//                                        .putHeader("Location", "/api/fruits/" + json.getLong("id"))
//                                        .putHeader("Content-Type", "application/json")
//                                        .setStatusCode(201)
//                                        .end(json.encodePrettily()),
//                        err -> writeError(ctx, err)
//                );
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

    private void deleteOne(RoutingContext ctx) {
//        store.delete(ctx.get("fruitId"))
//                .subscribe(
//                        () ->
//                                ctx.response()
//                                        .setStatusCode(204)
//                                        .end(),
//                        err -> {
//                            if (err instanceof NoSuchElementException) {
//                                error(ctx, 404, err);
//                            } else {
//                                error(ctx, 415, err);
//                            }
//                        }
//                );
    }

    private String getEnv(String key, String dv) {
        String s = System.getenv(key);
        if (s == null) {
            return dv;
        }
        return s;
    }
}
