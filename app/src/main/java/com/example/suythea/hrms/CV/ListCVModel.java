package com.example.suythea.hrms.CV;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListCVModel {

    private String name, job, experience, urlProfile, ex_salary, postedDate;

    public ListCVModel(String name, String job, String experience, String urlProfile, String ex_salary, String postedDate) {
        this.name = name;
        this.job = job;
        this.experience = experience;
        this.urlProfile = urlProfile;
        this.ex_salary = ex_salary;
        this.postedDate = postedDate;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public String getExperience() {
        return experience;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public String getEx_salary() {
        return ex_salary;
    }

    public String getPostedDate() {
        return postedDate;
    }
}
