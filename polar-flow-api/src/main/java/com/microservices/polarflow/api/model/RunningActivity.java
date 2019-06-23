package com.microservices.polarflow.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class RunningActivity extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="distance", nullable = false)
    private Double distance;

    @NotNull
    @Column(name="paceAvg", nullable = false)
    private String paceAvg;

    @NotNull
    @Column(name="paceMax", nullable = false)
    private String paceMax;

    @Column(name="ascent", nullable = true)
    @Min(value = 1, message = "Ascent should not be less than 1")
    @Max(value = 1000, message = "Ascent should not be greater than 1000")
    private Integer ascent;

    @Column(name="descent", nullable = true)
    @Min(value = 1, message = "Descent should not be less than 1")
    @Max(value = 1000, message = "Descent should not be greater than 1000")
    private Integer descent;

    // see: https://jogtunes.com
    @Column(name="cadenceAvg", nullable = true)
    @Min(value = 1, message = "Cadence Average should not be less than 1")
    @Max(value = 250, message = "Cadence Average should not be greater than 250")
    private Integer cadenceAvg;

    @Column(name="cadenceMax", nullable = true)
    @Min(value = 1, message = "Cadence Max should not be less than 1")
    @Max(value = 250, message = "Cadence Max should not be greater than 250")
    private Integer cadenceMax;

    @Column(name="runningIndex", nullable = true)
    @Min(value = 1, message = "Running Index should not be less than 1")
    @Max(value = 150, message = "Running Index should not be greater than 150")
    private Integer runningIndex;

    @JsonIgnore
    @OneToMany(mappedBy = "running", cascade = CascadeType.ALL)
    private List<Activity> activities;

    public RunningActivity() { }

    public RunningActivity(@NotNull Double distance, @NotNull String paceAvg, @NotNull String paceMax, @Min(value = 1, message = "Ascent should not be less than 1") @Max(value = 1000, message = "Ascent should not be greater than 1000") Integer ascent, @Min(value = 1, message = "Descent should not be less than 1") @Max(value = 1000, message = "Descent should not be greater than 1000") Integer descent, @Min(value = 1, message = "Cadence Average should not be less than 1") @Max(value = 250, message = "Cadence Average should not be greater than 250") Integer cadenceAvg, @Min(value = 1, message = "Cadence Max should not be less than 1") @Max(value = 250, message = "Cadence Max should not be greater than 250") Integer cadenceMax, @Min(value = 1, message = "Running Index should not be less than 1") @Max(value = 150, message = "Running Index should not be greater than 150") Integer runningIndex) {
        this.distance = distance;
        this.paceAvg = paceAvg;
        this.paceMax = paceMax;
        this.ascent = ascent;
        this.descent = descent;
        this.cadenceAvg = cadenceAvg;
        this.cadenceMax = cadenceMax;
        this.runningIndex = runningIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAscent() {
        return ascent;
    }

    public void setAscent(Integer ascent) {
        this.ascent = ascent;
    }

    public Integer getDescent() {
        return descent;
    }

    public void setDescent(Integer descent) {
        this.descent = descent;
    }

    public Integer getCadenceAvg() {
        return cadenceAvg;
    }

    public void setCadenceAvg(Integer cadenceAvg) {
        this.cadenceAvg = cadenceAvg;
    }

    public Integer getCadenceMax() {
        return cadenceMax;
    }

    public void setCadenceMax(Integer cadenceMax) {
        this.cadenceMax = cadenceMax;
    }

    public Integer getRunningIndex() {
        return runningIndex;
    }

    public void setRunningIndex(Integer runningIndex) {
        this.runningIndex = runningIndex;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    private void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
