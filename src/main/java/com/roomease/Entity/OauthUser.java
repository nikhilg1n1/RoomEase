package com.roomease.Entity;

import jakarta.persistence.*;

@Entity

public class OauthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sub;
    private String name;
    @Column(name ="Email_address")
    private String email;
    private String picture;

    public OauthUser(String sub  ,String name, String email, String picture) {
        this.sub = sub;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public OauthUser() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
