package com.microservices.nutritionist.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NutritionistApplication {

    // if want to use jaeger tracing, must start a new span in this API.
    // see: https://shekhargulati.com/2019/04/08/a-minimalistic-guide-to-distributed-tracing-with-opentracing-and-jaeger/

    public static void main(String[] args) {
        SpringApplication.run(NutritionistApplication.class, args);
    }

}
