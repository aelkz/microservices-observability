package com.microservices.polarflow.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservices.polarflow.api.instrument.listener.ActivityListener;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EntityListeners(ActivityListener.class)
public class Activity extends BaseModel {

    public static enum Mood {
        AWFUL, FUGLY, MEH, GOOD, GREAT;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @Past
    @Column(name="startDate", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Past
    @Column(name="endDate", nullable = false)
    private LocalDateTime endDate;

    @NotNull
    @Min(value = 30, message = "Heart rate avg should not be less than 30")
    @Max(value = 220, message = "Heart rate avg should not be greater than 220")
    @Column(name="hrAgv", nullable = false)
    private Integer hrAvg;

    @NotNull
    @Min(value = 30, message = "Heart rate min should not be less than 30")
    @Max(value = 220, message = "Heart rate min should not be greater than 220")
    @Column(name="hrMin", nullable = false)
    private Integer hrMin;

    @NotNull
    @Min(value = 30, message = "Heart rate max should not be less than 30")
    @Max(value = 220, message = "Heart rate max should not be greater than 220")
    @Column(name="hrMax", nullable = false)
    private Integer hrMax;

    @NotNull
    @Max(value = 100, message = "Burned fat should not be greater than 100 percent")
    @Column(name="burnedFat", nullable = false)
    private Integer burnedFat;

    @NotNull
    @Min(value = 1, message = "Total calories burned should not be less than 1 kcal")
    @Max(value = 10000, message = "Total calories burned should not be greater than 10000 kcal")
    @Column(name="calories", nullable = false)
    private Integer calories;

    // used to determine total training load (for recovery) in hours.
    @NotNull
    @Column(name="load", nullable = false)
    @Min(value = 1, message = "Training load should not be less than 1 hour")
    @Max(value = 168, message = "Training load should not be greater than 168 hours")
    private Integer load;

    @Column(name="notes", nullable = true)
    private String notes;

    // relationships

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="device_id", referencedColumnName = "id", nullable = false)
    private Device device;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sport_id", referencedColumnName = "id", nullable = false)
    private Sport sport;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, optional = true)
    @JoinColumn(name="running_id", referencedColumnName = "id")
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

    @Transient
    @JsonIgnore
    public Boolean isRunningActivity() {
        if (this.getRunning() != null) {
            if (this.getRunning().getDistance().intValue() > 0) {
                return true;
            }
        }
        return false;
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
