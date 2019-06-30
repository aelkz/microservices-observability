package com.microservices.strava.api.repository;

import com.microservices.strava.api.model.Event;

public class EventRepository extends BaseRepository<Event> {

    public EventRepository() {
        super(Event.class);
    }
}
