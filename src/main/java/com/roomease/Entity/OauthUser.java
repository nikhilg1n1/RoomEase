package com.roomease.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity

public class OauthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sub;
    private String name;
    @Column(name ="Email_address" , unique = true , nullable = false)
    private String email;
    private String picture;
    @Column(nullable = false)
    private String provider;

    private String password;

    private boolean verified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;

    public OauthUser(Long id, String sub, String name, String email, String picture, String provider, String password, boolean verified, UserRole userRole) {
        this.id = id;
        this.sub = sub;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.password = password;
        this.verified = verified;
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }


    public OauthUser(String sub  ,String name, String email, String picture,String provider,String password,UserRole userRole) {
        this.sub = sub;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.provider=provider;
        this.password=password;
        this.userRole=userRole;

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

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
