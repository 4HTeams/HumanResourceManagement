package com.example.suythea.hrms.ViewOwnJob;

/**
 * Created by lolzzlolzz on 6/28/16.
 */
public class ListViewOwnJobModel {

    private String title, exp, salary, deadline;

    public ListViewOwnJobModel(String title, String exp, String salary, String deadline) {
        this.title = title;
        this.exp = "Experience : " + exp;
        this.salary = "Salary : " + salary;
        this.deadline = "Deadline : " + deadline;
    }

    public String getTitle() {
        return title;
    }

    public String getExp() {
        return exp;
    }

    public String getSalary() {
        return salary;
    }

    public String getDeadline() {
        return deadline;
    }
}
