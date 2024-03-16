package com.example.authtechsphere.model;

public class UserModel {
    String email, uid, username, image, phone, pwd, isFaculty;

    public UserModel(String email, String id, String username, String image, String phone, String pwd,String isFaculty) {
        this.email = email;
        this.uid = id;
        this.username = username;
        this.image = image;
        this.phone = phone;
        this.pwd = pwd;
        this.isFaculty = isFaculty;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getIsFaculty() {
        return isFaculty;
    }

    public void setIsFaculty(String isFaculty) {
        this.isFaculty = isFaculty;
    }

    public UserModel() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return uid;
    }

    public void setId(String id) {
        this.uid = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
