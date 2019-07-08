package com.microservices.polarflow.api.instrument.metrics;

public interface BaseMetrics<T> {

    public void loadMetrics(T e);

}
