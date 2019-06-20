package com.microservices.calendar.api.configuration;


import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.jdbc.JDBCClient;
import rx.Completable;
import rx.Observable;

public class InitConfiguration {

    private InitConfiguration() { }

    public static Completable init(Vertx vertx) {
        return Completable.complete();
    }
}
