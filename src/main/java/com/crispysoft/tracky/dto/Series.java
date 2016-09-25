package com.crispysoft.tracky.dto;

/**
 * User: david
 * Date: 25.09.2016
 * Time: 11:31
 */
public class Series {
    private String x;
    private String y;

    public Series() {
    }

    public Series(String x, String y) {
        this.x = x;
        this.y = y;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
