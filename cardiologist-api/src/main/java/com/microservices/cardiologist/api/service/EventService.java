package com.microservices.cardiologist.api.service;

import com.microservices.cardiologist.api.model.Event;
import com.microservices.cardiologist.api.repository.EventRepository;
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
