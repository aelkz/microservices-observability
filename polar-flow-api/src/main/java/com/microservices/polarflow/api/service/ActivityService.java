package com.microservices.polarflow.api.service;

import com.microservices.polarflow.api.model.Activity;
import com.microservices.polarflow.api.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public Activity save(Activity e) {
        return repository.save(e);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
