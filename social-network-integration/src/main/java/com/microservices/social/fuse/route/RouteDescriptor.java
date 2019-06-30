package com.microservices.social.fuse.route;

public enum RouteDescriptor {

    REST_CONFIG( "/social", "social-api", "fuse-social-network-integration-api"),
    REST_POST_STRAVA( "/strava", "post-fuse-strava-integration-api", "POST fuse-strava-integration-api"),
    INTERNAL_POST_STRAVA( "direct:internal-strava", "post-strava-api", "POST strava-api");

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
