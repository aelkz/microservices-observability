package com.microservices.strava.api.repository;

import com.microservices.strava.api.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EventRepository extends BaseRepository<Event> {

    @PersistenceContext
    private EntityManager em;

    public EventRepository() {
        super(Event.class);
    }
}
