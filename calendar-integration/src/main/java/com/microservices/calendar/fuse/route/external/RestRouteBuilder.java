package com.microservices.calendar.fuse.route.external;

import com.microservices.calendar.fuse.configuration.GoogleCalendarConfiguration;
import com.microservices.calendar.fuse.processor.APIKeyNotFoundExceptionProcessor;
import com.microservices.calendar.fuse.processor.ExceptionProcessor;
import com.microservices.calendar.fuse.route.RouteDescriptor;
import com.microservices.calendar.fuse.route.internal.InternalRouteBuilder;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class RestRouteBuilder extends RouteBuilder {

    static final Logger logger = LoggerFactory.getLogger(RestRouteBuilder.class);

    @Autowired
    private Environment env;

    @Value("${api.version}")
    private String apiVersion;

    @Autowired
    private GoogleCalendarConfiguration calendarConfig;

    @Autowired
    private APIKeyNotFoundExceptionProcessor apiKeyNotFoundExceptionProcessor;

    @Override
    public void configure() throws Exception {

        // /--------------------------------------------------\
        // | Configure REST endpoint                          |
        // \--------------------------------------------------/

        restConfiguration()
            .apiContextPath("/api-docs")
            .apiProperty("api.title", "fuse-calendar-integration-api")
            .apiProperty("api.version", apiVersion)
            //.apiProperty("cors", "true")
            //
            .component("servlet")
            .contextPath("/api/v" + apiVersion)
            .dataFormatProperty("prettyPrint", "true")
            .port(env.getProperty("server.port", "8080"))
            .bindingMode(RestBindingMode.json);

        // /--------------------------------------------------\
        // | Expose route w/ REST endpoint                    |
        // \--------------------------------------------------/

        rest(RouteDescriptor.REST_CONFIG.getUri()).id(RouteDescriptor.REST_CONFIG.getId())
            .description(RouteDescriptor.REST_CONFIG.getDescription())
            .consumes(MediaType.APPLICATION_JSON).produces(MediaType.APPLICATION_JSON)

        .post(RouteDescriptor.REST_POST_CALENDAR.getUri())
            .id(RouteDescriptor.REST_POST_CALENDAR.getId())
            .description(RouteDescriptor.REST_POST_CALENDAR.getDescription())
            .responseMessage().code(204).message("success").endResponseMessage()
                .route().routeId(RouteDescriptor.REST_POST_CALENDAR.getId())
                .log(LoggingLevel.INFO, logger, "received request from ${header.CamelHttpServletRequest.remoteAddr}")
                .log(LoggingLevel.INFO, logger, "checking the existence of google-calendar user api key into http header")
                .choice()
                    .when(header(calendarConfig.getApiKeyName()).isNotNull())
                    //.log(LoggingLevel.INFO, logger, "calling google-calendar api with api key=${header.google-api-integration-key}")
                    .log(LoggingLevel.INFO, logger, "calling google-calendar api with api key=${header.Google-Calendar-API-User-Key}")
                    .to(RouteDescriptor.INTERNAL_POST_CALENDAR.getUri())
                .otherwise()
                    .process(apiKeyNotFoundExceptionProcessor)
                    .log(LoggingLevel.WARN, logger, "google-calendar user api key not found into http header")
                .endChoice()
            .endRest();

    }

}
