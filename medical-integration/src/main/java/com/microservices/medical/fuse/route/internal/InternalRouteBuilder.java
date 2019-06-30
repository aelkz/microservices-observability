package com.microservices.medical.fuse.route.internal;

import com.microservices.medical.fuse.configuration.CardiologistConfiguration;
import com.microservices.medical.fuse.configuration.NutritionistConfiguration;
import com.microservices.medical.fuse.processor.ExceptionProcessor;
import com.microservices.medical.fuse.processor.LogHeaderProcessor;
import com.microservices.medical.fuse.route.RouteDescriptor;
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
    private NutritionistConfiguration nutritionistConfig;

    @Autowired
    private CardiologistConfiguration cardiologistConfig;

    @Autowired
    private ExceptionProcessor exceptionProcessor;

    @Autowired
    private LogHeaderProcessor logHeaderProcessor;

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
            .handled(true)
            .process(exceptionProcessor)
            .redeliveryDelay(150)
            .maximumRedeliveries(2) // 1 + 2 retries
            .to("log:exception");

        // /--------------------------------------------------\
        // | Internal route definition                        |
        // \--------------------------------------------------/

        from(RouteDescriptor.INTERNAL_POST_NUTRITIONIST.getUri())
            .log(LoggingLevel.WARN, logger, "internal route: preparing to call external api using http4 producer")
            .marshal().json(JsonLibrary.Jackson)
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
            .setHeader(nutritionistConfig.getApiKeyName(), header(nutritionistConfig.getApiKeyName()))
            .process(logHeaderProcessor)
            .removeHeader(Exchange.HTTP_PATH)
            .to("http4://"+nutritionistConfig.getHost()+":"+nutritionistConfig.getPort()+nutritionistConfig.getContextPath()+"?connectTimeout=500&bridgeEndpoint=true")
            .unmarshal().json(JsonLibrary.Jackson)
            .end();

        from(RouteDescriptor.INTERNAL_POST_CARDIOLOGIST.getUri())
            .log(LoggingLevel.WARN, logger, "internal route: preparing to call external api using http4 producer")
            .marshal().json(JsonLibrary.Jackson)
            .setHeader(Exchange.HTTP_METHOD, constant(HttpMethod.POST))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
            .setHeader(cardiologistConfig.getApiKeyName(), header(cardiologistConfig.getApiKeyName()))
            .process(logHeaderProcessor)
            .removeHeader(Exchange.HTTP_PATH)
            .to("http4://"+cardiologistConfig.getHost()+":"+cardiologistConfig.getPort()+cardiologistConfig.getContextPath()+"?connectTimeout=500&bridgeEndpoint=true")
            .unmarshal().json(JsonLibrary.Jackson)
            .end();

    }
}
