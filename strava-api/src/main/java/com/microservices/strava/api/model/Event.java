package com.microservices.strava.api.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e")
})
public class Event extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventSequence")
    @SequenceGenerator(name = "eventSequence", sequenceName = "event_id_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    @NotNull
    @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters")
    @Column(name="email", nullable = false)
    @Email(message = "Invalid email")
    private String email;

    @NotNull
    @Column(name="handle", nullable = false)
    private String handle;

    @NotNull
    @Column(name="calories", nullable = false)
    @Min(value = 1, message = "Total calories burned should not be less than 1 kcal")
    @Max(value = 10000, message = "Total calories burned should not be greater than 10000 kcal")
    private Integer calories;

    @NotNull
    @Column(name="startDate", nullable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime startDate;

    @NotNull
    @Column(name="endDate", nullable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime endDate;

    @NotNull
    @Column(name="distance", nullable = false)
    private Double distance;

    @NotNull
    @Column(name="paceAvg", nullable = false)
    private String paceAvg;

    @NotNull
    @Column(name="paceMax", nullable = false)
    private String paceMax;

    public Event() { }

    public Event(@NotNull @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters") String email, @NotNull String handle, @NotNull Integer calories, @NotNull LocalDateTime startDate, @NotNull LocalDateTime endDate, @NotNull Double distance, @NotNull String paceAvg, @NotNull String paceMax) {
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
        Event event = (Event) o;
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
