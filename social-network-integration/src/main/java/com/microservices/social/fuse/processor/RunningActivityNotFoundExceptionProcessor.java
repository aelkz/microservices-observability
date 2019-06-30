package com.microservices.social.fuse.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RunningActivityNotFoundExceptionProcessor implements Processor {

    private static final transient Logger logger = LoggerFactory.getLogger(RunningActivityNotFoundExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody("{\"error\": \"" + "Activity does not contain running data" +"\"}");
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.BAD_REQUEST.value());
    }
}
