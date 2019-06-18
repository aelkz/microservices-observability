package com.microservices.nutritionist.api.repository;

import com.microservices.nutritionist.api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> { }
