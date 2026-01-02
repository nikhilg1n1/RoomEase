package com.roomease.Entity;

import jakarta.persistence.*;

@Entity

public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="Email_address")
    private String email;

    private String password;

    private String picture;

    public UserInfo(Long id, String email, String password, String picture) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public UserInfo() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
