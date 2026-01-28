package com.roomease.DTO;

import com.roomease.Entity.UserRole;
import lombok.Data;
import org.apache.catalina.User;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<String> getRole() {
        return roles;
    }

    public void setRole(List<String> roles) {
        this.roles = roles;
    }

    private List<String> roles;


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

    public UserDataCache() {
    }

    public UserDataCache(String id, String name, String email, String picture,String provider,String password,List<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider=provider;
        this.password=password;
        this.roles=roles;

    }

    public UserDataCache(String email , List<String> roles, String provider,String picture){
        this.email=email;
        this.roles=roles;
        this.provider=provider;
        this.picture=picture;
    }



}
