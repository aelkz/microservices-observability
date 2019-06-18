package com.microservices.strava.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Event extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters")
    @Column(name="email", nullable = false)
    private String email;

    @NotNull
    @Column(name="handle", nullable = false)
    private String handle;

    @NotNull
    @Column(name="calories", nullable = false)
    private Double calories;

    @NotNull
    @Column(name="startDate", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name="endDate", nullable = false)
    private LocalDateTime endDate;

    @NotNull
    @Column(name="distance", nullable = false)
    private Double distance;

    @NotNull
    @Column(name="avgPace", nullable = false)
    private Double avgPace;

    @NotNull
    @Column(name="avgHeartRate", nullable = false)
    private Double avgHeartRate;

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

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
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

    public Double getAvgPace() {
        return avgPace;
    }

    public void setAvgPace(Double avgPace) {
        this.avgPace = avgPace;
    }

    public Double getAvgHeartRate() {
        return avgHeartRate;
    }

    public void setAvgHeartRate(Double avgHeartRate) {
        this.avgHeartRate = avgHeartRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getId().equals(event.getId()) &&
                getEmail().equals(event.getEmail()) &&
                getHandle().equals(event.getHandle()) &&
                getCalories().equals(event.getCalories()) &&
                getStartDate().equals(event.getStartDate()) &&
                getEndDate().equals(event.getEndDate()) &&
                getDistance().equals(event.getDistance());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getHandle(), getCalories(), getStartDate(), getEndDate(), getDistance());
    }
}
