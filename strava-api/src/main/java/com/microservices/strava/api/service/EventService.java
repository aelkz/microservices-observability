package com.microservices.strava.api.service;

import com.microservices.strava.api.model.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class EventService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(Transactional.TxType.REQUIRED)
    public Event save(Event e) {
        return null;
    }

}
