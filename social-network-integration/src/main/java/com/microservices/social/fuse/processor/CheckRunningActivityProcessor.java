package com.microservices.social.fuse.processor;

import com.microservices.social.fuse.model.Activity;
import com.microservices.social.fuse.model.Event;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CheckRunningActivityProcessor implements Processor {

    private static final transient Logger logger = LoggerFactory.getLogger(CheckRunningActivityProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Activity activity = exchange.getIn().getBody(Activity.class);

        if (activity.getRunning() == null) {
            // Event data don't have running records
            Map<String,Object> headers = exchange.getIn().getHeaders();
            headers.put("NO_RUNNING_ACTIVITY", true);
            exchange.getOut().setBody("");
            exchange.getOut().setHeaders(headers);
        }

    }
}
