package com.microservices.nutritionist.api.service;

import com.microservices.nutritionist.api.model.Event;
import com.microservices.nutritionist.api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository repository;

    public Event save(Event e) {
        return repository.save(e);
    }

}
