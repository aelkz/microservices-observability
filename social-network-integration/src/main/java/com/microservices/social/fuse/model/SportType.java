package com.microservices.social.fuse.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class SportType extends BaseModel {

    private Long id;

    private String name;

    private List<Sport> sports;

    public SportType() { }

    public SportType(@NotNull @Size(min = 3, max = 255, message = "Sport type name must be between 3 and 255 characters") String name, List<Sport> sports) {
        this.name = name;
        this.sports = sports;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Sport> getSports() {
        return sports;
    }

    public void setSports(List<Sport> sports) {
        this.sports = sports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportType sportType = (SportType) o;
        return getId().equals(sportType.getId()) &&
                getName().equals(sportType.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
