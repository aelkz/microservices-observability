package com.microservices.strava.api.service;

import com.microservices.strava.api.model.Event;
import com.microservices.strava.api.repository.EventRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

@Stateless
public class EventService {

    @Inject
    private javax.enterprise.event.Event<Event> eventSrc;

    @Inject
    private EventRepository repository;

    private final String API_KEY = "Strava-API-User-Key";

    @Transactional(Transactional.TxType.REQUIRED)
    public Event save(Event e) {
        repository.create(e);
        eventSrc.fire(e);
        return null;
    }

    /**
     * 1. Check if API Key exists in header
     * 2. Check if API Key is valid (pending)
     * @param headers
     * @return
     */
    public Boolean checkApiKey(HttpHeaders headers) {
        MultivaluedMap<String, String> map = headers.getRequestHeaders();
        return map.containsKey(API_KEY);
    }

}
