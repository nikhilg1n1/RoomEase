package com.roomease.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDataCache implements Serializable {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    private  String sub;
    private String name;
    private String email;
    private String picture;
    private String provider;
    private String password;



    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String role;

    public UserDataCache() {
    }

    public UserDataCache(String id, String name, String email, String picture,String provider,String password,String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider=provider;
        this.password=password;
        this.role=role;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
