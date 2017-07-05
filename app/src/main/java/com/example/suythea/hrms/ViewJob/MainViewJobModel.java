package com.example.suythea.hrms.ViewJob;

/**
 * Created by raa-jr on 6/23/17.
 */

public class MainViewJobModel {

    String title,desc,position,salary,deadline,
            experience,contype,province,carlvl,degree;

    public MainViewJobModel(String title, String desc, String position,
                            String salary, String deadline, String experience,
                            String contype, String province, String carlvl,
                            String degree) {
        this.title = title;
        this.desc = desc;
        this.position = position;
        this.salary = salary;
        this.deadline = deadline;
        this.experience = experience;
        this.contype = contype;
        this.province = province;
        this.carlvl = carlvl;
        this.degree = degree;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getPosition() {
        return position;
    }

    public String getSalary() {
        return salary;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getExperience() {
        return experience;
    }

    public String getContype() {
        return contype;
    }

    public String getProvince() {
        return province;
    }

    public String getCarlvl() {
        return carlvl;
    }

    public String getDegree() {
        return degree;
    }
}
