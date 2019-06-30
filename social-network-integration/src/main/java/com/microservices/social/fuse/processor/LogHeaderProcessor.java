package com.microservices.social.fuse.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogHeaderProcessor implements Processor {

    private static final transient Logger logger = LoggerFactory.getLogger(LogHeaderProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("--- START-HEADER VALUES ---");
        exchange.getIn().getHeaders().forEach((k,v) -> {
            System.out.println(k+"="+v);
        });
        System.out.println("--- END-HEADER VALUES ---");
    }
}
