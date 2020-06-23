package com.microservices.calendar.api.configuration;

import io.reactivex.Completable;
import io.vertx.reactivex.core.Vertx;

public class InitConfiguration {

    private InitConfiguration() { }

    public static Completable init(Vertx vertx) {
        return Completable.complete();
    }
}
