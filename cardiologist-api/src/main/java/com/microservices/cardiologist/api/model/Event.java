package com.microservices.cardiologist.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Event extends BaseModel {

    public static enum Gender {
        M, F;
    }

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
    @Column(name="birthDate", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Column(name="gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name="calories", nullable = false)
    private Double calories;

    @NotNull
    @Column(name="sportId", nullable = false)
    private String sportId;

    @NotNull
    @Column(name="startDate", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name="endDate", nullable = false)
    private LocalDateTime endDate;

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

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return getId().equals(event.getId()) &&
                getEmail().equals(event.getEmail()) &&
                getGender() == event.getGender() &&
                getCalories().equals(event.getCalories()) &&
                Objects.equals(getSportId(), event.getSportId()) &&
                getStartDate().equals(event.getStartDate()) &&
                getEndDate().equals(event.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getGender(), getCalories(), getSportId(), getStartDate(), getEndDate());
    }
}
