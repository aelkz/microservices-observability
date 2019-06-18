package com.microservices.cardiologist.api.repository;

import com.microservices.cardiologist.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> { }
