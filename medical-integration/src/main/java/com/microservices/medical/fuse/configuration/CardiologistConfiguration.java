package com.microservices.medical.fuse.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cardiologist")
public class CardiologistConfiguration {

    private String host;
    private String port;
    private String contextPath;
    private String apiKeyName;

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

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getApiKeyName() {
        return apiKeyName;
    }

    public void setApiKeyName(String apiKeyName) {
        this.apiKeyName = apiKeyName;
    }
}
