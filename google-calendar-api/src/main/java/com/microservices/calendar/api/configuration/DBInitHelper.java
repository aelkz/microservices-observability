package com.microservices.calendar.api.configuration;

import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.jdbc.JDBCClient;
import rx.Completable;
import rx.Observable;

/**
 * Simple helper to bootstrap your Database.
 *
 * @author Paulo Lopes
 */
public class DBInitHelper {

    private DBInitHelper() {
        // Private constructor.
    }

    public static Completable initDatabase(Vertx vertx, JDBCClient jdbc) {
        return null;
//        return jdbc.rxGetConnection()
//                .flatMapCompletable(connection ->
//                        vertx.fileSystem().rxReadFile("ddl.sql")
//                                .flatMapObservable(buffer -> Observable.from(buffer.toString().split(";")))
//                                .flatMapSingle(connection::rxExecute)
//                                .doAfterTerminate(connection::close)
//                                .toCompletable()
//                );
    }
}
