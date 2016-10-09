package com.crispysoft.tracky.dto;

import java.util.Date;

/**
 * User: david
 * Date: 09.10.2016
 * Time: 14:42
 */
public class WeekData {
    Date date;
    String dateLabel;
    int calories;
    Weight weight;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateLabel() {
        return dateLabel;
    }

    public void setDateLabel(String dateLabel) {
        this.dateLabel = dateLabel;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }
}
