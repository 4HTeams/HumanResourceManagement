package com.example.suythea.hrms.CV;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListCVModel {

    private String fName, lName, postedDate, title;

    public ListCVModel(String fName, String lName, String postedDate, String title) {

        this.fName = fName;
        this.lName = lName;
        this.postedDate = postedDate;
        this.title = title;
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
}
