package com.vientamthuong.eatsimple.model;

import com.vientamthuong.eatsimple.date.DateTime;

public class Account {
    private String id;
    private String username;
    private String password;
    private String email;
    private String forgotPasswordId;
    private String img;
    private String imgLink;
    private String name;
    private DateTime dateCreated;
    private String expireDateCode;


    public Account(String id, String username, String password, String email, String forgotPasswordId, String img, String imgLink, String name, DateTime dateCreated, String expireDateCode) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.forgotPasswordId = forgotPasswordId;
        this.img = img;
        this.imgLink = imgLink;
        this.name = name;
        this.dateCreated = dateCreated;
        this.expireDateCode = expireDateCode;
    }
    public Account(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getForgotPasswordId() {
        return forgotPasswordId;
    }

    public void setForgotPasswordId(String forgotPasswordId) {
        this.forgotPasswordId = forgotPasswordId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getExpireDateCode() {
        return expireDateCode;
    }

    public void setExpireDateCode(String expireDateCode) {
        this.expireDateCode = expireDateCode;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", forgotPasswordId='" + forgotPasswordId + '\'' +
                ", img='" + img + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", expireDateCode='" + expireDateCode + '\'' +
                '}';
    }
}
