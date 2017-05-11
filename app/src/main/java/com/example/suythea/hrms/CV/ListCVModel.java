package com.example.suythea.hrms.CV;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListCVModel {

    private String fName;
    private String lName;
    private String postedDate;
    private String title;
    private String id;
    private String uid;

    public ListCVModel(String fName, String lName, String postedDate, String title, String id, String uid) {
        this.fName = fName;
        this.lName = lName;
        this.postedDate = postedDate;
        this.title = title;
        this.id = id;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
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
