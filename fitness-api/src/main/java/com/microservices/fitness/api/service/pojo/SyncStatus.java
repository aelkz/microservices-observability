package com.microservices.fitness.api.service.pojo;

import io.opentracing.Span;

import java.util.Objects;

public class SyncStatus {

    private Boolean synced;

    private Span childSpan;

    public SyncStatus() { }

    public SyncStatus(Boolean synced) {
        this.synced = synced;
    }

    public Boolean getSynced() {
        return synced;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }

    public Span getChildSpan() {
        return childSpan;
    }

    public void setChildSpan(Span childSpan) {
        this.childSpan = childSpan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SyncStatus that = (SyncStatus) o;
        return getSynced().equals(that.getSynced());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSynced());
    }
}
