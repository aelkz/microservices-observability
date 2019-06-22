package com.microservices.polarflow.api.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "integration.social")
public class SocialIntegrationConfiguration {

    private String host;
    private String port;
    private String path;
    private String myFitnessPalKey;
    private String appleHealthKey;

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

    public String getMyFitnessPalKey() {
        return myFitnessPalKey;
    }

    public void setMyFitnessPalKey(String myFitnessPalKey) {
        this.myFitnessPalKey = myFitnessPalKey;
    }

    public String getAppleHealthKey() {
        return appleHealthKey;
    }

    public void setAppleHealthKey(String appleHealthKey) {
        this.appleHealthKey = appleHealthKey;
    }
}
