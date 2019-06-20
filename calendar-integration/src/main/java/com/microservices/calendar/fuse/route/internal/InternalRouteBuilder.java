package com.microservices.calendar.fuse.route.internal;

import com.microservices.calendar.fuse.configuration.GoogleCalendarConfiguration;
import com.microservices.calendar.fuse.processor.ExceptionProcessor;
import com.microservices.calendar.fuse.route.RouteDescriptor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.undertow.UndertowComponent;
import org.apache.camel.component.undertow.UndertowHostOptions;
import org.apache.camel.component.undertow.springboot.UndertowComponentConfiguration;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class InternalRouteBuilder extends RouteBuilder {

    static final Logger logger = LoggerFactory.getLogger(InternalRouteBuilder.class);

    @Autowired
    private GoogleCalendarConfiguration calendarConfig;

    @Autowired
    private ExceptionProcessor exceptionProcessor;

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
            .handled(true)
            .process(exceptionProcessor);

        // /--------------------------------------------------\
        // | Internal route definition                        |
        // \--------------------------------------------------/

        // for testing purposes: http://ipinfo.io/ip
        // https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/7.0-tp/html/apache_camel_component_reference/undertow-component

        from(RouteDescriptor.INTERNAL_POST_CALENDAR.getUri())
            .log(LoggingLevel.WARN, logger, "internal route: preparing to call external api using undertow producer")
            .marshal().json(JsonLibrary.Jackson)
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
            //.setHeader(InfinispanConstants.KEY, constant("${header.google-api-integration-key}"))
            .setHeader(calendarConfig.getApiKeyName(), header(calendarConfig.getApiKeyName()))
            .to("undertow:http://"+calendarConfig.getHost()+":"+calendarConfig.getPort()+calendarConfig.getContextPath()+"?options.read-timeout=1")
            .end();

    }
}
