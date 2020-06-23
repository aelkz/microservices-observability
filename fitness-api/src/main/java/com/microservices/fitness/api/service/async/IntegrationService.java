package com.microservices.fitness.api.service.async;

import java.util.concurrent.CompletableFuture;

public interface IntegrationService<T,A> {

    CompletableFuture<T> sendAsyncEvent(A payload);

}
