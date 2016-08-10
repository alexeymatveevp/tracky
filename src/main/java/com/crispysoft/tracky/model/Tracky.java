package com.crispysoft.tracky.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * User: david
 * Date: 10.08.2016
 * Time: 21:09
 */
public class Tracky {
    @Id
    String id;
    String text;
    Date start;
    Date end;
    String project;

    public Tracky() {
    }

    public Tracky(String text, Date start, Date end, String project) {
        this.text = text;
        this.start = start;
        this.end = end;
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
