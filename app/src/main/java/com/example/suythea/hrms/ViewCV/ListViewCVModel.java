package com.example.suythea.hrms.ViewCV;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListViewCVModel {

    private String fName, lName, pDate, title;

    public ListViewCVModel(String fName, String lName, String pDate, String title) {
        this.fName = "First Name : " + fName;
        this.lName = "Last Name : " + lName;
        this.pDate = "Posted Date : " + pDate;
        this.title = title;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getpDate() {
        return pDate;
    }

    public String getTitle() {
        return title;
    }
}
