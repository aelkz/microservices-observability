package com.microservices.polarflow.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integration.medical")
public class MedicalIntegrationConfiguration {

    private String host;
    private String port;
    private String path;
    private String nutritionistKey;
    private String cardiologistKey;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNutritionistKey() {
        return nutritionistKey;
    }

    public void setNutritionistKey(String nutritionistKey) {
        this.nutritionistKey = nutritionistKey;
    }

    public String getCardiologistKey() {
        return cardiologistKey;
    }

    public void setCardiologistKey(String cardiologistKey) {
        this.cardiologistKey = cardiologistKey;
    }
}
