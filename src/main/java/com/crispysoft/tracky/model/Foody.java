package com.crispysoft.tracky.model;


import org.springframework.data.annotation.Id;

import java.util.Date;

public class Foody {
    @Id
    String id;
    String name;
    String person;
    Date date;
    Double weight;

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

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
