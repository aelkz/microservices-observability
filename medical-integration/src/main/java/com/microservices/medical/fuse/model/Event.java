package com.microservices.medical.fuse.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Event extends BaseModel {

    public static enum Gender {
        M, F;
    }

    private Long id;

    private String email;

    private String handle;

    private LocalDate birthDate;

    private Event.Gender gender;

    private Integer calories;

    private Integer burnedFat;

    private String sportId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Integer hrAvg;

    private Integer hrMin;

    private Integer hrMax;

    // used to determine total training load (for recovery) in hours.
    private Integer load;

    private String notes;

    public Event() { }

    public Event(@NotNull @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters") String email, @NotNull String handle, @NotNull LocalDate birthDate, @NotNull Gender gender, @NotNull Integer calories, @NotNull @Max(value = 100, message = "Burned fat should not be greater than 100 percent") Integer burnedFat, @NotNull String sportId, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, @NotNull @Min(value = 30, message = "Heart rate avg should not be less than 30") @Max(value = 220, message = "Heart rate avg should not be greater than 220") Integer hrAvg, @NotNull @Min(value = 30, message = "Heart rate min should not be less than 30") @Max(value = 220, message = "Heart rate min should not be greater than 220") Integer hrMin, @NotNull @Min(value = 30, message = "Heart rate max should not be less than 30") @Max(value = 220, message = "Heart rate max should not be greater than 220") Integer hrMax, @NotNull @Min(value = 1, message = "Training load should not be less than 1 hour") @Max(value = 168, message = "Training load should not be greater than 168 hours") Integer load, String notes) {
        this.email = email;
        this.handle = handle;
        this.birthDate = birthDate;
        this.gender = gender;
        this.calories = calories;
        this.burnedFat = burnedFat;
        this.sportId = sportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hrAvg = hrAvg;
        this.hrMin = hrMin;
        this.hrMax = hrMax;
        this.load = load;
        this.notes = notes;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Integer getBurnedFat() {
        return burnedFat;
    }

    public void setBurnedFat(Integer burnedFat) {
        this.burnedFat = burnedFat;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getId().equals(event.getId()) &&
                getEmail().equals(event.getEmail()) &&
                Objects.equals(getHandle(), event.getHandle()) &&
                getBirthDate().equals(event.getBirthDate()) &&
                getGender() == event.getGender() &&
                getCalories().equals(event.getCalories()) &&
                getSportId().equals(event.getSportId()) &&
                getStartDate().equals(event.getStartDate()) &&
                getEndDate().equals(event.getEndDate()) &&
                getHrAvg().equals(event.getHrAvg()) &&
                getHrMin().equals(event.getHrMin()) &&
                getHrMax().equals(event.getHrMax()) &&
                getLoad().equals(event.getLoad()) &&
                Objects.equals(getNotes(), event.getNotes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getHandle(), getBirthDate(), getGender(), getCalories(), getSportId(), getStartDate(), getEndDate(), getHrAvg(), getHrMin(), getHrMax(), getLoad(), getNotes());
    }
}
