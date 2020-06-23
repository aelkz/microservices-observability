package com.microservices.calendar.api.adapter;

import io.opentracing.propagation.TextMap;
import io.vertx.reactivex.core.MultiMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Raphael Abreu
 */
public class ReactiveMultiMapExtractAdapter implements TextMap {

    private MultiMap headers;

    public ReactiveMultiMapExtractAdapter(MultiMap headers) {
        this.headers = headers;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return headers.entries().iterator();
    }

    @Override
    public void put(String key, String value) {
        throw new UnsupportedOperationException();
    }
}
