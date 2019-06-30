package com.microservices.strava.api.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class BaseRepository<T> {

    @PersistenceContext(unitName = "primary")
    public EntityManager em;

    private Class<T> entityClass;

    public BaseRepository() {
    }

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void delete(Long id) {
        T entity = em.find(entityClass, id);
        if (entity != null) {
            em.remove(entity);
        }
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    public T find(Long id) {
        return em.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return em.createQuery("select t from " + entityClass.getSimpleName() + " t").getResultList();
    }

}
