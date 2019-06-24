package com.microservices.medical.fuse.route.external;

import com.microservices.medical.fuse.configuration.CardiologistConfiguration;
import com.microservices.medical.fuse.configuration.NutritionistConfiguration;
import com.microservices.medical.fuse.processor.APIKeyNotFoundExceptionProcessor;
import com.microservices.medical.fuse.route.RouteDescriptor;
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
    private NutritionistConfiguration nutritionistConfig;

    @Autowired
    private CardiologistConfiguration cardiologistConfig;

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

            .post(RouteDescriptor.REST_POST_NUTRITIONIST.getUri())
                .id(RouteDescriptor.REST_POST_NUTRITIONIST.getId())
                .description(RouteDescriptor.REST_POST_NUTRITIONIST.getDescription())
                .responseMessage().code(204).message("success").endResponseMessage()
                    .route().routeId(RouteDescriptor.REST_POST_NUTRITIONIST.getId())
                    .log(LoggingLevel.INFO, logger, "received request from ${header.CamelHttpServletRequest.remoteAddr}")
                    .log(LoggingLevel.INFO, logger, "checking the existence of nutritionist user api key into http header")
                    .choice()
                        .when(header(nutritionistConfig.getApiKeyName()).isNotNull())
                        .log(LoggingLevel.INFO, logger, "calling google-calendar api with api key=${header.Nutritionist-API-User-Key}")
                        .to(RouteDescriptor.INTERNAL_POST_NUTRITIONIST.getUri())
                    .otherwise()
                        .process(apiKeyNotFoundExceptionProcessor)
                        .log(LoggingLevel.WARN, logger, "google-calendar user api key not found into http header")
                    .endChoice()
            .endRest()

            .post(RouteDescriptor.REST_POST_CARDIOLOGIST.getUri())
                .id(RouteDescriptor.REST_POST_CARDIOLOGIST.getId())
                .description(RouteDescriptor.REST_POST_CARDIOLOGIST.getDescription())
                .responseMessage().code(204).message("success").endResponseMessage()
                    .route().routeId(RouteDescriptor.REST_POST_CARDIOLOGIST.getId())
                    .log(LoggingLevel.INFO, logger, "received request from ${header.CamelHttpServletRequest.remoteAddr}")
                    .log(LoggingLevel.INFO, logger, "checking the existence of google-calendar user api key into http header")
                    .choice()
                        .when(header(cardiologistConfig.getApiKeyName()).isNotNull())
                        .log(LoggingLevel.INFO, logger, "calling google-calendar api with api key=${header.Cardiologist-API-User-Key}")
                        .to(RouteDescriptor.INTERNAL_POST_CARDIOLOGIST.getUri())
                    .otherwise()
                        .process(apiKeyNotFoundExceptionProcessor)
                        .log(LoggingLevel.WARN, logger, "google-calendar user api key not found into http header")
                .endChoice()
            .endRest();

    }

}
