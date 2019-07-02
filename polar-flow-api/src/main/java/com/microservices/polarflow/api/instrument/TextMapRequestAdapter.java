package com.microservices.polarflow.api.instrument;

import io.opentracing.propagation.TextMap;
import org.springframework.http.HttpHeaders;
import java.util.Iterator;
import java.util.Map;

public class TextMapRequestAdapter implements TextMap {
    private final HttpHeaders httpHeaders;

    public TextMapRequestAdapter(HttpHeaders headers) {
        this.httpHeaders = headers;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        throw new UnsupportedOperationException("carrier is writer-only");
    }

    @Override
    public void put(String key, String value) {
        httpHeaders.add(key, value);
    }
}
