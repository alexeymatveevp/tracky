package com.crispysoft.tracky.model;


import com.crispysoft.tracky.util.DF;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Foody {
    @Id
    String id;
    String name;
    String person;
    Date date;
    Double weight;
    @Transient
    Double calories = 8.0;

    public Foody(String name, String person, Date date, Double weight) {
        this.name = name;
        this.person = person;
        this.date = date;
        this.weight = weight;
    }

    public Foody() {
    }

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

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
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

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
