package com.microservices.social.fuse.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ExceptionProcessor implements Processor {

    private static final transient Logger logger = LoggerFactory.getLogger(ExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception exception = (Exception) exchange.getProperty(Exchange.EXCEPTION_CAUGHT);

        logger.info(exception.getMessage());
        logger.info(exception.getLocalizedMessage());
        exception.printStackTrace();

        exchange.getIn().setBody("{\"error\": \"" + exception.getMessage() +"\"}");
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
