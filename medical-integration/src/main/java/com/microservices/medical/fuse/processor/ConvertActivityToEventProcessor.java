package com.microservices.medical.fuse.processor;

import com.microservices.medical.fuse.model.Activity;
import com.microservices.medical.fuse.model.Event;
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
        Event e = new Event();
        BeanUtils.copyProperties(activity,e);

        e.setEmail(activity.getUser().getEmail());
        e.setHandle(activity.getUser().getHandle());
        e.setBirthDate(activity.getUser().getBirthDate());
        e.setGender(("M".equals(activity.getUser().getGender().name()) ? Event.Gender.M : Event.Gender.F));
        e.setSportId(activity.getSport().getId()+"-"+activity.getSport().getName());

        exchange.getOut().setBody(e);
        exchange.getIn().setBody(e);
    }
}
