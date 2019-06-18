package com.microservices.calendar.api.service;

import io.vertx.core.json.JsonObject;
import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * A CRUD to SQL interface
 */
public interface Store {

    Single<JsonObject> create(JsonObject item);

    Observable<JsonObject> readAll();

    Single<JsonObject> read(long id);

    Completable update(long id, JsonObject item);

    Completable delete(long id);
}
