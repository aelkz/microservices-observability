package com.microservices.medical.fuse;

import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import javax.enterprise.event.Observes;
//import org.apache.camel.cdi.ContextName;
//import org.apache.camel.spi.CamelEvent.CamelContextStartingEvent;

@SpringBootApplication
@CamelOpenTracing
public class MedicalFuseApplication {

    private static Logger logger = LoggerFactory.getLogger(MedicalFuseApplication.class);

    @Value("${camel.component.servlet.mapping.contextPath}")
    private String contextPath;

    public static void main(String[] args) {
        SpringApplication.run(MedicalFuseApplication.class, args);
    }

}
