package com.microservices.medical.fuse.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Activity extends BaseModel {

    public static enum Mood {
        AWFUL, FUGLY, MEH, GOOD, GREAT;
    }

    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer hrAvg;

    private Integer hrMin;

    private Integer hrMax;

    private Integer burnedFat;

    private Integer calories;

    // used to determine total training load (for recovery) in hours.
    private Integer load;

    private String notes;

    // relationships

    private Device device;

    private Sport sport;

    private User user;

    private RunningActivity running;

    public Activity() { }

    public Activity(LocalDateTime startDate, LocalDateTime endDate, Integer hrAvg, Integer hrMin, Integer hrMax, Integer burnedFat, Integer calories, Integer load, String notes) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.hrAvg = hrAvg;
        this.hrMin = hrMin;
        this.hrMax = hrMax;
        this.burnedFat = burnedFat;
        this.calories = calories;
        this.load = load;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getHrAvg() {
        return hrAvg;
    }

    public void setHrAvg(Integer hrAvg) {
        this.hrAvg = hrAvg;
    }

    public Integer getHrMin() {
        return hrMin;
    }

    public void setHrMin(Integer hrMin) {
        this.hrMin = hrMin;
    }

    public Integer getHrMax() {
        return hrMax;
    }

    public void setHrMax(Integer hrMax) {
        this.hrMax = hrMax;
    }

    public Integer getBurnedFat() {
        return burnedFat;
    }

    public void setBurnedFat(Integer burnedFat) {
        this.burnedFat = burnedFat;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getLoad() {
        return load;
    }

    public void setLoad(Integer load) {
        this.load = load;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RunningActivity getRunning() {
        return running;
    }

    public void setRunning(RunningActivity running) {
        this.running = running;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return getId().equals(activity.getId()) &&
                getStartDate().equals(activity.getStartDate()) &&
                getEndDate().equals(activity.getEndDate()) &&
                Objects.equals(getHrAvg(), activity.getHrAvg()) &&
                getHrMin().equals(activity.getHrMin()) &&
                getHrMax().equals(activity.getHrMax()) &&
                getBurnedFat().equals(activity.getBurnedFat()) &&
                getCalories().equals(activity.getCalories()) &&
                getLoad().equals(activity.getLoad()) &&
                Objects.equals(getNotes(), activity.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartDate(), getEndDate(), getHrAvg(), getHrMin(), getHrMax(), getBurnedFat(), getCalories(), getLoad(), getNotes());
    }
}
