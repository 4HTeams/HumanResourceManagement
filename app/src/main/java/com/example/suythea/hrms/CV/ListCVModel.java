package com.example.suythea.hrms.CV;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListCVModel {

    private String fName, lName, postedDate, title, id;

    public ListCVModel(String fName, String lName, String postedDate, String title, String id) {
        this.fName = fName;
        this.lName = lName;
        this.postedDate = postedDate;
        this.title = title;
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
