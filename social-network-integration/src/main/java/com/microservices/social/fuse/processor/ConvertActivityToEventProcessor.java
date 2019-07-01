package com.microservices.social.fuse.processor;

import com.microservices.social.fuse.model.Activity;
import com.microservices.social.fuse.model.StravaEvent;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ConvertActivityToEventProcessor implements Processor {

    private static final transient Logger logger = LoggerFactory.getLogger(ConvertActivityToEventProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        Activity activity = exchange.getIn().getBody(Activity.class);

        if (activity.getRunning() != null) {
            StravaEvent e = new StravaEvent();
            BeanUtils.copyProperties(activity,e);

            e.setEmail(activity.getUser().getEmail());
            e.setHandle(activity.getUser().getHandle());
            e.setCalories(activity.getCalories());
            e.setStartDate(activity.getStartDate());
            e.setEndDate(activity.getEndDate());
            // exclusive running data
            e.setDistance(activity.getRunning().getDistance());
            e.setPaceAvg(activity.getRunning().getPaceAvg());
            e.setPaceMax(activity.getRunning().getPaceMax());
            e.setId(null);

            exchange.getOut().setBody(e);
            exchange.getOut().setHeaders(exchange.getIn().getHeaders());
        }

    }
}
