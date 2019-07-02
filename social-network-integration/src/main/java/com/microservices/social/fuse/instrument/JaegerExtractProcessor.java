package com.microservices.social.fuse.instrument;

import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMap;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class JaegerExtractProcessor implements Processor {

    private static final transient Logger logger = LoggerFactory.getLogger(JaegerExtractProcessor.class);

    @Autowired
    private Tracer tracer;

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String,Object> headers = exchange.getIn().getHeaders();

        if (headers != null && headers.size() > 0) {
            TextMap headersTextMap = new TextMapRequestAdapter(headers);

            SpanContext parentContext = tracer.extract(Format.Builtin.HTTP_HEADERS, headersTextMap);

            Span span = tracer.buildSpan("strava.social.camel.svc").asChildOf(parentContext).start();
            span.finish();
        }

    }
}
