package com.microservices.polarflow.api.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
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
    private Double calories;

    // used to determine total training load (for recovery) in hours.
    @NotNull
    @Column(name="load", nullable = false)
    @Min(value = 1, message = "Training load should not be less than 1 hour")
    @Max(value = 168, message = "Training load should not be greater than 168 hours")
    private Integer load;

    @NotNull
    @Column(name="notes", nullable = true)
    private String notes;

    // relationships

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="device_id", referencedColumnName = "id")
    private Device device;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sport_id", referencedColumnName = "id")
    private Sport sport;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    // 3rd party application api key
    // The API Keys will be used to simulate an optional integration across the synchronization process.
    // The user can switch 3rd party app integrations on/off.

    @Column(name="stravaApiKey", nullable = true)
    private String stravaApiKey;

    @Column(name="nutritionistApiKey", nullable = true)
    private String nutritionistApiKey;

    @Column(name="cardiologistApiKey", nullable = true)
    private String cardiologistApiKey;

    @Column(name="googleCalendarApiKey", nullable = true)
    private String googleCalendarApiKey;

    public Activity() { }

    public Activity(LocalDateTime startDate, LocalDateTime endDate, Integer hrAvg, Integer hrMin, Integer hrMax, Integer burnedFat, Double calories, Integer load, String notes, String stravaApiKey, String nutritionistApiKey, String cardiologistApiKey, String googleCalendarApiKey) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.hrAvg = hrAvg;
        this.hrMin = hrMin;
        this.hrMax = hrMax;
        this.burnedFat = burnedFat;
        this.calories = calories;
        this.load = load;
        this.notes = notes;
        this.stravaApiKey = stravaApiKey;
        this.nutritionistApiKey = nutritionistApiKey;
        this.cardiologistApiKey = cardiologistApiKey;
        this.googleCalendarApiKey = googleCalendarApiKey;
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

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
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

    public String getStravaApiKey() {
        return stravaApiKey;
    }

    public void setStravaApiKey(String stravaApiKey) {
        this.stravaApiKey = stravaApiKey;
    }

    public String getNutritionistApiKey() {
        return nutritionistApiKey;
    }

    public void setNutritionistApiKey(String nutritionistApiKey) {
        this.nutritionistApiKey = nutritionistApiKey;
    }

    public String getCardiologistApiKey() {
        return cardiologistApiKey;
    }

    public void setCardiologistApiKey(String cardiologistApiKey) {
        this.cardiologistApiKey = cardiologistApiKey;
    }

    public String getGoogleCalendarApiKey() {
        return googleCalendarApiKey;
    }

    public void setGoogleCalendarApiKey(String googleCalendarApiKey) {
        this.googleCalendarApiKey = googleCalendarApiKey;
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
