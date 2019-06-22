package com.microservices.polarflow.api.service.pojo;

import java.util.Objects;

public class SyncStatus {

    private Boolean synced;

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
