package com.microservices.medical.fuse.route;

public enum RouteDescriptor {

    REST_CONFIG( "/medical", "medical-api", "fuse-medical-integration-api"),
    REST_POST_NUTRITIONIST( "/", "post-fuse-nutritionist-integration-api", "POST fuse-nutritionist-integration-api"),
    INTERNAL_POST_NUTRITIONIST( "direct:internal-nutritionist", "post-nutritionist-api", "POST nutritionist-api"),
    REST_POST_CARDIOLOGIST( "/", "post-fuse-cardiologist-integration-api", "POST fuse-cardiologist-integration-api"),
    INTERNAL_POST_CARDIOLOGIST( "direct:internal-cardiologist", "post-cardiologist-api", "POST cardiologist-api");

    private String uri;
    private String id;
    private String description;

    RouteDescriptor(String uri, String id, String description) {
        this.uri = uri;
        this.id = id;
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
