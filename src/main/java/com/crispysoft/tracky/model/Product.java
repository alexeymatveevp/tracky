package com.crispysoft.tracky.model;

import org.springframework.data.annotation.Id;

/**
 * Created by kristina on 22.09.2016.
 */
public class Product {
    @Id
    String name;
    Integer ccal;
    Double p;
    Double c;
    Double f;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCcal() {
        return ccal;
    }

    public void setCcal(Integer ccal) {
        this.ccal = ccal;
    }

    public Double getP() {
        return p;
    }

    public void setP(Double p) {
        this.p = p;
    }

    public Double getC() {
        return c;
    }

    public void setC(Double c) {
        this.c = c;
    }

    public Double getF() {
        return f;
    }

    public void setF(Double f) {
        this.f = f;
    }
}
