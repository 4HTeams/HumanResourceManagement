package com.example.suythea.hrms.PostCV;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListPostCVModel {

    private String title, date, name, level, degree;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDegree() {

        return degree;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTitle() {

        return title;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }
}
