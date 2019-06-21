package com.microservices.calendar.fuse.route.internal;

import com.microservices.calendar.fuse.configuration.GoogleCalendarConfiguration;
import com.microservices.calendar.fuse.processor.ExceptionProcessor;
import com.microservices.calendar.fuse.route.RouteDescriptor;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
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
            .process(exceptionProcessor)
            .redeliveryDelay(150)
            .maximumRedeliveries(3)
            .to("log:exception");

        // /--------------------------------------------------\
        // | Internal route definition                        |
        // \--------------------------------------------------/

        // for testing purposes: http://ipinfo.io/ip
        // https://access.redhat.com/documentation/en-us/red_hat_jboss_fuse/7.0-tp/html/apache_camel_component_reference/undertow-component
        // https://github.com/apache/camel/blob/master/components/camel-http4/src/main/docs/http4-component.adoc
        // https://access.redhat.com/solutions/1528693

        from(RouteDescriptor.INTERNAL_POST_CALENDAR.getUri())
            .log(LoggingLevel.WARN, logger, "internal route: preparing to call external api using http4 producer")
            .marshal().json(JsonLibrary.Jackson)
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
            //.setHeader(InfinispanConstants.KEY, constant("${header.google-api-integration-key}"))
            .setHeader(calendarConfig.getApiKeyName(), header(calendarConfig.getApiKeyName()))
            .process((e) -> {
                e.getIn().getHeaders().forEach((k,v) -> {
                    System.out.println(k+"="+v);
                });
            })
            .removeHeader(Exchange.HTTP_PATH)
            .to("http4://"+calendarConfig.getHost()+":"+calendarConfig.getPort()+calendarConfig.getContextPath()+"?connectTimeout=500&bridgeEndpoint=true")
            .unmarshal().json(JsonLibrary.Jackson)
            .end();

    }
}
