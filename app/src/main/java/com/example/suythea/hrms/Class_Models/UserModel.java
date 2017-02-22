package com.example.suythea.hrms.Class_Models;

/**
 * Created by lolzzlolzz on 2/22/17.
 */

public class UserModel {

    int uid;
    String username,email,profile_url,type,approval;

    public UserModel() {
        this.username = "";
        this.email = "";
        this.profile_url = "";
        this.type = "";
        this.approval = "";
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public int getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public String getType() {
        return type;
    }

    public String getApproval() {
        return approval;
    }
}
