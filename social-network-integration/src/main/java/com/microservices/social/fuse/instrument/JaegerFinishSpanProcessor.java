package com.microservices.social.fuse.instrument;

import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JaegerFinishSpanProcessor implements Processor {

    private static final transient Logger logger = LoggerFactory.getLogger(JaegerFinishSpanProcessor.class);

    @Autowired
    private Tracer tracer;

    @Override
    public void process(Exchange exchange) throws Exception {
            Span span = tracer.activeSpan();
            span.finish();
    }

}
