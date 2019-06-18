package com.microservices.polarflow.api.repository;

import com.microservices.polarflow.api.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> { }
