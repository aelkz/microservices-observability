package com.microservices.polarflow.api.instrument.tracer;

import com.google.common.collect.ImmutableMap;
import com.microservices.polarflow.api.instrument.RequestBuilderCarrier;
import com.microservices.polarflow.api.model.Activity;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
public class ActivityTracer implements BaseTrace<Activity> {

    @Autowired
    private Tracer tracer;

    @Override
    public Span startRootSpan(String message) {
        return tracer.buildSpan(message).start();
    }

    @Override
    public Span startChildSpan(Span rootSpan, String message) {
        return tracer.buildSpan(message).asChildOf(rootSpan).start();
    }

    @Override
    public void finishSpan(Span s) {
        s.finish();
    }

    @Override
    public void setHttpStatusTag(Span s, HttpStatus status) {
        s.setTag(BaseTrace.HTTP_STATUS_CODE_TAG, status.value());
    }

    @Override
    public void setCustomTag(Span s, String message, Number value) {
        s.setTag(message,value);
    }

    @Override
    public void setCustomTag(Span s, String message, String value) {
        s.setTag(message, value);
    }

    @Override
    public void setCustomTag(Span s, String message, Boolean value) {
        s.setTag(message, value);
    }

    @Override
    public void log(Span s, ImmutableMap<String, Object> immutableMap) {
        s.log(immutableMap);
    }

    @Override
    public void log(Span s, String message) {
        s.log(message);
    }

    @Override
    public void inject(String uri, HttpHeaders httpHeaders, HttpMethod httpMethod) {
        Tags.SPAN_KIND.set(getTracer().activeSpan(), Tags.SPAN_KIND_CLIENT);
        Tags.HTTP_METHOD.set(getTracer().activeSpan(), httpMethod.name());
        Tags.HTTP_URL.set(getTracer().activeSpan(), uri);
        getTracer().inject(getTracer().activeSpan().context(), Format.Builtin.HTTP_HEADERS, new RequestBuilderCarrier(httpHeaders));
    }

    /**
     * Example of tracer.extract() method
     * (It will be used on integration services, and 3rd party APIs later)
     *
     * @GetMapping(path = "/random")
     *     public String name(@RequestHeader HttpHeaders headers) {
     *         SpanContext parentContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
     *         Span span = tracer.buildSpan("find-random-scientist-name").asChildOf(parentContext).start();
     *         String name = scientistsNames.get(random.nextInt(scientistsNames.size()));
     *         span.finish();
     *         return name;
     *     }
     * @return
     */

    public Tracer getTracer() {
        return tracer;
    }

    public void setTracer(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityTracer that = (ActivityTracer) o;
        return getTracer().equals(that.getTracer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTracer());
    }
}
