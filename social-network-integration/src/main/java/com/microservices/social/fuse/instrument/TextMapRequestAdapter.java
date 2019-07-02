package com.microservices.social.fuse.instrument;

import io.opentracing.propagation.TextMap;
import java.util.Iterator;
import java.util.Map;

public class TextMapRequestAdapter implements TextMap {
    private final Map<String,Object> httpHeaders;

    public TextMapRequestAdapter(Map<String,Object> headers) {
        this.httpHeaders = headers;
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        throw new UnsupportedOperationException("carrier is writer-only");
    }

    @Override
    public void put(String key, String value) {
        if (value instanceof String) {
            httpHeaders.put(key, (String) value);
        }else {
            httpHeaders.put(key, value.toString());
        }
    }

}
