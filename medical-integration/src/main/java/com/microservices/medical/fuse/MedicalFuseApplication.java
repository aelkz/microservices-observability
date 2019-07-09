package com.microservices.medical.fuse;

import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * There are three ways in which an OpenTracing tracer can be configured to provide distributed tracing for a Camel application.
 * https://github.com/apache/camel/blob/master/components/camel-opentracing/src/main/docs/opentracing.adoc
 * https://github.com/redhat-developer-demos/istio-tutorial/blob/master/customer/java/camel-springboot/src/main/java/com/redhat/developer/demos/CustomerCamelRoute.java
 * https://access.redhat.com/solutions/3606001
 */

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
