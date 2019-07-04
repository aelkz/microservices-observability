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

    public Activity save(Activity a) {
        Activity entity = repository.saveAndFlush(a);
        repository.refresh(entity);
        return entity;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Activity get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void refresh(Activity a) {
        repository.refresh(a);
    }

}
