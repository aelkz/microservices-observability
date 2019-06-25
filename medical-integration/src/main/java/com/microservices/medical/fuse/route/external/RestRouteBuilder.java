package com.microservices.medical.fuse.route.external;

import com.microservices.medical.fuse.configuration.CardiologistConfiguration;
import com.microservices.medical.fuse.configuration.NutritionistConfiguration;
import com.microservices.medical.fuse.model.Activity;
import com.microservices.medical.fuse.model.Event;
import com.microservices.medical.fuse.processor.APIKeyNotFoundExceptionProcessor;
import com.microservices.medical.fuse.route.RouteDescriptor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.json.simple.JsonObject;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import java.util.LinkedHashMap;
import java.util.Map;

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
            .apiProperty("api.title", "fuse-medical-integration-api")
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
                        .log(LoggingLevel.INFO, logger, "calling nutritionist api with api key=${header.Nutritionist-API-User-Key}")
                        .process(p-> {
                            System.out.println();
                            Map object = p.getIn().getBody(LinkedHashMap.class);

                            // {id=7, startDate=[2019, 6, 20, 19, 0], endDate=[2019, 6, 20, 19, 58, 21], hrAvg=165, hrMin=120, hrMax=181, burnedFat=21, calories=741, load=11, notes=feel a little stress on left shoulder, device={id=2, name=Polar V800}, sport={id=13, name=Weight Lifting, type={id=1, name=General}}, user={id=1, email=rabreu@redhat.com, handle=@aelkz, firstName=raphael, lastName=abreu, birthDate=[1984, 10, 16], gender=M, weight=99.4, height=182.0, measurementUnit=METRIC, hrMax=192, hrRest=55, vo2max=42, trainingBackground=HEAVY, dailyActivityGoal=L1, stravaApiKey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdHJhdmEuY29tL2F1dGgiLCJzdWIiOiJyYXBoYWVsLmFsZXhAZ21haWwuY29tIiwibmFtZSI6IlJhcGhhZWwgQWJyZXUiLCJpYXQiOjE1MTYyMzkwMjJ9.kic8uctcWS0BmjxpXicA78Uv-TDoqoJlzPtBTsoVTPA, nutritionistApiKey=0292eaa7-0618-43d6-ae3e-17253489e42a, cardiologistApiKey=952cf92d-0b5f-490c-84d6-551210c0c4b3, googleCalendarApiKey=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjYWxlbmRhci5nb29nbGUuY29tL2F1dGgiLCJzdWIiOiJyYXBoYWVsLmFsZXhAZ21haWwuY29tIiwibmFtZSI6IlJhcGhhZWwgQWJyZXUiLCJpYXQiOjE1MTYyMzkwMjJ9.rDpZA5xp_Xi_Vknw0BOJKcXtkrPdm6A9c806u8OGsUE}, running=null}

                            System.out.println("[1]"+object);
                            System.out.println("[2]"+p.getIn().getBody());
                            System.out.println("[4]"+p.getIn().getBody().getClass());

                            Event e = new Event();
                            p.getOut().setBody(e);
                        })
                    .otherwise()
                        .process(apiKeyNotFoundExceptionProcessor)
                        .log(LoggingLevel.WARN, logger, "nutritionist user api key not found into http header")
                    .endChoice()
            .endRest()

            .post(RouteDescriptor.REST_POST_CARDIOLOGIST.getUri())
                .id(RouteDescriptor.REST_POST_CARDIOLOGIST.getId())
                .description(RouteDescriptor.REST_POST_CARDIOLOGIST.getDescription())
                .responseMessage().code(204).message("success").endResponseMessage()
                    .route().routeId(RouteDescriptor.REST_POST_CARDIOLOGIST.getId())
                    .log(LoggingLevel.INFO, logger, "received request from ${header.CamelHttpServletRequest.remoteAddr}")
                    .log(LoggingLevel.INFO, logger, "checking the existence of cardiologist user api key into http header")
                    .choice()
                        .when(header(cardiologistConfig.getApiKeyName()).isNotNull())
                        .log(LoggingLevel.INFO, logger, "calling cardiologist api with api key=${header.Cardiologist-API-User-Key}")
                        .to(RouteDescriptor.INTERNAL_POST_CARDIOLOGIST.getUri())
                    .otherwise()
                        .process(apiKeyNotFoundExceptionProcessor)
                        .log(LoggingLevel.WARN, logger, "cardiologist user api key not found into http header")
                .endChoice()
            .endRest();

    }

}
