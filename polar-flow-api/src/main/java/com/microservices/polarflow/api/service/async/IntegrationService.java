package com.microservices.polarflow.api.service.async;

import java.util.concurrent.Future;

public interface IntegrationService<T,A> {

    Future<T> sendEvent(A payload);

}
