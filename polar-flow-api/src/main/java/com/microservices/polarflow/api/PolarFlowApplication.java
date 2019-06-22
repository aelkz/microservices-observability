package com.microservices.polarflow.api;

import com.microservices.polarflow.api.repository.impl.CustomRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class PolarFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(PolarFlowApplication.class, args);
    }

}
