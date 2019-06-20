package com.microservices.calendar.api.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.web.RoutingContext;

public class Errors {

    public static void error(RoutingContext ctx, int status, String cause) {
        JsonObject error = new JsonObject()
            .put("error", cause)
            .put("code", status)
            .put("path", ctx.request().path());
        ctx.response()
            .putHeader("Content-Type", "application/json")
            .setStatusCode(status)
            .end(error.encodePrettily());
    }

    public static void error(RoutingContext ctx, int status, Throwable cause) {
        error(ctx, status, cause.getMessage());
    }


}
