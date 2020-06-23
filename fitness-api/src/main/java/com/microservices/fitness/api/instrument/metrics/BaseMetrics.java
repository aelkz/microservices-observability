package com.microservices.fitness.api.instrument.metrics;

public interface BaseMetrics<T> {

    public void loadMetrics(T e);

}
