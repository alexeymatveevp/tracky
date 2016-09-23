package com.crispysoft.tracky.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by kristina on 22.09.2016.
 */
public class PersonProgress {
    @Id
    String id;
    String name;
    Date date;
    Double weight;
    Double breast;
    Double waist;
    Double hips;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBreast() {
        return breast;
    }

    public void setBreast(Double breast) {
        this.breast = breast;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Double getHips() {
        return hips;
    }

    public void setHips(Double hips) {
        this.hips = hips;
    }
}
