package com.microservices.social.fuse;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import org.apache.camel.opentracing.starter.CamelOpenTracing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@CamelOpenTracing
public class SocialFuseApplication {

    private static Logger logger = LoggerFactory.getLogger(SocialFuseApplication.class);

    @Value("${camel.component.servlet.mapping.contextPath}")
    private String contextPath;

    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE)
                .withParam(1);

        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                .withFlushInterval(1000)
                .withMaxQueueSize(10000)
                .withLogSpans(true);

        Configuration config = new Configuration("fuse-social-network-integration-api-svc")
                .withSampler(samplerConfig)
                .withReporter(reporterConfig);

        return config.getTracer();
    }

    public static void main(String[] args) {
        SpringApplication.run(SocialFuseApplication.class, args);
    }

}
