package com.microservices.social.fuse.processor;

import io.micrometer.core.instrument.MeterRegistry;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionProcessor implements Processor {

    @Autowired
    MeterRegistry registry;

    private static final transient Logger logger = LoggerFactory.getLogger(ExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);

        // counter to count different types of messages received
        registry.counter("counter.integration.social.exception", "value", exception.getMessage()).increment();

        //   Counter counter = Counter
        //      .builder("instance")
        //      .description("indicates instance count of the object")
        //      .tags("dev", "performance")
        //      .register(registry);

        logger.info(exception.getMessage());

        exchange.getIn().setBody("{\"error\": \"" + exception.getMessage() +"\"}");
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
