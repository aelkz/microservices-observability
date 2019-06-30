package com.microservices.social.fuse.route.external;

import com.microservices.social.fuse.configuration.StravaConfiguration;
import com.microservices.social.fuse.model.Activity;
import com.microservices.social.fuse.processor.APIKeyNotFoundExceptionProcessor;
import com.microservices.social.fuse.processor.CheckRunningActivityProcessor;
import com.microservices.social.fuse.processor.ConvertActivityToEventProcessor;
import com.microservices.social.fuse.processor.RunningActivityNotFoundExceptionProcessor;
import com.microservices.social.fuse.route.RouteDescriptor;
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
    private StravaConfiguration stravaConfig;

    @Autowired
    private CheckRunningActivityProcessor checkRunningActivityProcessor;

    @Autowired
    private APIKeyNotFoundExceptionProcessor apiKeyNotFoundExceptionProcessor;

    @Autowired
    private RunningActivityNotFoundExceptionProcessor runningActivityNotFoundExceptionProcessor;

    @Autowired
    private ConvertActivityToEventProcessor convertActivityToEventProcessor;

    @Override
    public void configure() throws Exception {

        // /--------------------------------------------------\
        // | Configure REST endpoint                          |
        // \--------------------------------------------------/

        restConfiguration()
            .apiContextPath("/api-docs")
            .apiProperty("api.title", "fuse-social-network-integration-api")
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

            .post(RouteDescriptor.REST_POST_STRAVA.getUri())
                .type(Activity.class)
                .id(RouteDescriptor.REST_POST_STRAVA.getId())
                .description(RouteDescriptor.REST_POST_STRAVA.getDescription())
                .responseMessage().code(204).message("success").endResponseMessage()
                    .route().routeId(RouteDescriptor.REST_POST_STRAVA.getId())
                    .log("route:"+RouteDescriptor.REST_POST_STRAVA.getUri())
                    .log(LoggingLevel.INFO, logger, "received request from ${header.CamelHttpServletRequest.remoteAddr}")
                    .log(LoggingLevel.INFO, logger, "checking the existence of running data")
                    .process(checkRunningActivityProcessor)
                    .log(LoggingLevel.INFO, logger, "checking the existence of strava user api key into http header")
                    .choice()
                        .when(header("NO_RUNNING_ACTIVITY").contains(true))
                            .process(runningActivityNotFoundExceptionProcessor)
                            .log(LoggingLevel.WARN, logger, "running data not found into activity event")
                        .when(header(stravaConfig.getApiKeyName()).isNotNull())
                            .log(LoggingLevel.INFO, logger, "calling strava api with api key=${header.Strava-API-User-Key}")
                            .process(convertActivityToEventProcessor)
                            .to(RouteDescriptor.INTERNAL_POST_STRAVA.getUri())
                    .otherwise()
                        .process(apiKeyNotFoundExceptionProcessor)
                        .log(LoggingLevel.WARN, logger, "strava user api key not found into http header")
                    .endChoice()
            .endRest();

    }

}
