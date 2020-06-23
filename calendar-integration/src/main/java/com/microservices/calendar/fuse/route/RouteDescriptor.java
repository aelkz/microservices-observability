package com.microservices.calendar.fuse.route;

public enum RouteDescriptor {

    REST_CONFIG( "/calendar", "calendar-api", "fuse-calendar-integration-api"),
    REST_POST_CALENDAR( "/", "post-fuse-calendar-integration-api", "POST fuse-calendar-integration-api"),
    INTERNAL_POST_CALENDAR( "direct:internal-reactive-calendar", "post-reactive-calendar-api", "POST reactive-calendar-api");

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
