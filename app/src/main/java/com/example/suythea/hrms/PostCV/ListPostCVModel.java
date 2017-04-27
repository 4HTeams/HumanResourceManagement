package com.example.suythea.hrms.PostCV;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListPostCVModel {

    private String title, date, cName;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcName() {

        return cName;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
