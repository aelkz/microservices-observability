package com.microservices.social.fuse.configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.spring.autoconfigure.MeterRegistryCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {

    @Value("${host}")
    private String host;

    @Value("${service}")
    private String service;

    @Value("${region}")
    private String region;

//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
//        return registry -> {
//            registry.config().commonTags("application", "social-network-metrics.svc");
//        };
//    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
        // registry.config().commonTags("region", "ua-east");
        return registry -> registry.config()
                .commonTags("host", host,
                        "service", service,
                        "region", region,
                        "application", "social-network-metrics.svc")
                .meterFilter(MeterFilter.deny(id -> {
                    String uri = id.getTag("uri");
                    return uri != null && uri.startsWith("/actuator");
                }))
                .meterFilter(MeterFilter.deny(id -> {
                    String uri = id.getTag("uri");
                    return uri != null && uri.contains("favicon");
                }));
    }

}
