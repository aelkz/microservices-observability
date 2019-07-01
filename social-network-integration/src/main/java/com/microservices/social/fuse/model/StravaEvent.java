package com.microservices.social.fuse.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

public class StravaEvent extends BaseModel {

    public static enum Gender {
        M, F;
    }

    private Long id;

    private String email;

    private String handle;

    private Integer calories;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double distance;

    private String paceAvg;

    private String paceMax;

    public StravaEvent() { }

    public StravaEvent(@NotNull @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters") String email, @NotNull String handle, @NotNull Integer calories, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, @NotNull Double distance, @NotNull String paceAvg, @NotNull String paceMax) {
        this.email = email;
        this.handle = handle;
        this.calories = calories;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
        this.paceAvg = paceAvg;
        this.paceMax = paceMax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getPaceAvg() {
        return paceAvg;
    }

    public void setPaceAvg(String paceAvg) {
        this.paceAvg = paceAvg;
    }

    public String getPaceMax() {
        return paceMax;
    }

    public void setPaceMax(String paceMax) {
        this.paceMax = paceMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StravaEvent event = (StravaEvent) o;
        return getId().equals(event.getId()) &&
                getEmail().equals(event.getEmail()) &&
                getHandle().equals(event.getHandle()) &&
                getCalories().equals(event.getCalories()) &&
                getStartDate().equals(event.getStartDate()) &&
                getEndDate().equals(event.getEndDate()) &&
                getDistance().equals(event.getDistance()) &&
                Objects.equals(getPaceAvg(), event.getPaceAvg()) &&
                Objects.equals(getPaceMax(), event.getPaceMax());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getHandle(), getCalories(), getStartDate(), getEndDate(), getDistance(), getPaceAvg(), getPaceMax());
    }

}
