package com.crispysoft.tracky.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by kristina on 25.09.2016.
 */
public class Product {
    @Id
    String id;
    String name;
    String runame;
    Double calories;
    Double protein;
    Double fat;
    Double сarbs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuname() {
        return runame;
    }

    public void setRuname(String runame) {
        this.runame = runame;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getСarbs() {
        return сarbs;
    }

    public void setСarbs(Double сarbs) {
        this.сarbs = сarbs;
    }
}
