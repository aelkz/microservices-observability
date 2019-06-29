package com.microservices.nutritionist.api.service;

import com.microservices.nutritionist.api.model.Event;
import com.microservices.nutritionist.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository repository;

    private final String API_KEY = "Nutritionist-API-User-Key";

    public Event save(Event e) {
        return repository.save(e);
    }

    /**
     * 1. Check if API Key exists in header
     * 2. Check if API Key is valid (pending)
     * @param headers
     * @return
     */
    public Boolean checkApiKey(HttpHeaders headers) {
        return headers.containsKey(API_KEY);
    }

}
