package com.microservices.fitness.api.instrument.tracer;

import com.google.common.collect.ImmutableMap;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

public interface BaseTrace<T> {

    public final String HTTP_STATUS_CODE_TAG = "http.status_code";

    public Span startRootSpan(String message);

    public Span startChildSpan(Span rootSpan, String message);

    public void finishSpan(Span s);

    public void setHttpStatusTag(Span s, HttpStatus status);

    public void setCustomTag(Span s, String message, Number value);

    public void setCustomTag(Span s, String message, String value);

    public void setCustomTag(Span s, String message, Boolean value);

    public void log(Span s, ImmutableMap<String,Object> immutableMap);

    public void log(Span s, String message);

    public void inject(String uri, HttpHeaders httpHeaders, HttpMethod httpMethod);

}
