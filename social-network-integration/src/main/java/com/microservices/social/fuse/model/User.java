package com.microservices.social.fuse.model;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class User extends BaseModel {

    public static enum Gender {
        M, F;
    }

    public static enum Measurement {
        METRIC, IMPERIAL;
    }

    public static enum TrainingBackground {
        OCCASIONAL, REGULAR, FREQUENT, HEAVY, SEMIPRO, PRO;
    }

    /**
     * L1 - If your day includes only a little sports and a lot of sitting, commuting by car or public transport and so on.
     * L2 - If you spend most of your day on your feet, perhaps due to the type of work you do or your daily chores.
     * L3 - If you work is physically demanding, you're into sports or otherwise tend to be on the move and active.
     */
    public static enum DailyActivityGoal {
        L1, L2, L3;
    }

    private Long id;

    private String email;

    private String handle;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private User.Gender gender;

    private Double weight;

    private Double height;

    private User.Measurement measurementUnit;

    private Integer hrMax;

    private Integer hrRest;

    private Integer vo2max;

    private User.TrainingBackground trainingBackground;

    private User.DailyActivityGoal dailyActivityGoal;

    // 3rd party application api key
    // The API Keys will be used to simulate an optional integration across the synchronization process.
    // The user can switch 3rd party app integrations on/off.

    private String stravaApiKey;

    private String nutritionistApiKey;

    private String cardiologistApiKey;

    private String reactiveCalendarApiKey;

    // relationships

    private List<Activity> activities;

    public User() { }

    public User(@NotNull @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters") String email, @NotNull String handle, @NotNull String firstName, @NotNull String lastName, @NotNull @Past LocalDate birthDate, @NotNull User.Gender gender, @NotNull Double weight, @NotNull Double height, @NotNull User.Measurement measurementUnit, @NotNull @Min(value = 140, message = "Heart rate max should not be less than 120") @Max(value = 220, message = "Heart rate max should not be greater than 220") Integer hrMax, @NotNull @Min(value = 30, message = "Heart rate rest should not be less than 30") @Max(value = 120, message = "Heart rate rest should not be greater than 120") Integer hrRest, @NotNull @Min(value = 1, message = "VO2max should not be less than 1") @Max(value = 120, message = "VO2max should not be greater than 120") Integer vo2max, @NotNull User.TrainingBackground trainingBackground, @NotNull User.DailyActivityGoal dailyActivityGoal, String stravaApiKey, String nutritionistApiKey, String cardiologistApiKey, String reactiveCalendarApiKey) {
        this.email = email;
        this.handle = handle;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.measurementUnit = measurementUnit;
        this.hrMax = hrMax;
        this.hrRest = hrRest;
        this.vo2max = vo2max;
        this.trainingBackground = trainingBackground;
        this.dailyActivityGoal = dailyActivityGoal;
        this.stravaApiKey = stravaApiKey;
        this.nutritionistApiKey = nutritionistApiKey;
        this.cardiologistApiKey = cardiologistApiKey;
        this.reactiveCalendarApiKey = reactiveCalendarApiKey;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Measurement getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(Measurement measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public Integer getHrMax() {
        return hrMax;
    }

    public void setHrMax(Integer hrMax) {
        this.hrMax = hrMax;
    }

    public Integer getHrRest() {
        return hrRest;
    }

    public void setHrRest(Integer hrRest) {
        this.hrRest = hrRest;
    }

    public Integer getVo2max() {
        return vo2max;
    }

    public void setVo2max(Integer vo2max) {
        this.vo2max = vo2max;
    }

    public TrainingBackground getTrainingBackground() {
        return trainingBackground;
    }

    public void setTrainingBackground(TrainingBackground trainingBackground) {
        this.trainingBackground = trainingBackground;
    }

    public DailyActivityGoal getDailyActivityGoal() {
        return dailyActivityGoal;
    }

    public void setDailyActivityGoal(DailyActivityGoal dailyActivityGoal) {
        this.dailyActivityGoal = dailyActivityGoal;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
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

    public String getReactiveCalendarApiKey() {
        return reactiveCalendarApiKey;
    }

    public void setReactiveCalendarApiKey(String reactiveCalendarApiKey) {
        this.reactiveCalendarApiKey = reactiveCalendarApiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) &&
                email.equals(user.email) &&
                Objects.equals(handle, user.handle) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                birthDate.equals(user.birthDate) &&
                gender == user.gender &&
                weight.equals(user.weight) &&
                height.equals(user.height) &&
                measurementUnit == user.measurementUnit &&
                Objects.equals(hrMax, user.hrMax) &&
                Objects.equals(hrRest, user.hrRest) &&
                Objects.equals(vo2max, user.vo2max) &&
                trainingBackground == user.trainingBackground &&
                dailyActivityGoal == user.dailyActivityGoal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, handle, firstName, lastName, birthDate, gender, weight, height, measurementUnit, hrMax, hrRest, vo2max, trainingBackground);
    }
}
