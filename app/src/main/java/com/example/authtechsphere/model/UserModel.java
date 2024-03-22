package com.example.authtechsphere.model;

public class UserModel {


    String email, uid, username, image, phone, password, isFaculty;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsFaculty() {
        return isFaculty;
    }

    public void setIsFaculty(String isFaculty) {
        this.isFaculty = isFaculty;
    }

    public UserModel(String email, String uid, String username, String image, String phone, String password, String isFaculty) {
        this.email = email;
        this.uid = uid;
        this.username = username;
        this.image = image;
        this.phone = phone;
        this.password = password;
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
