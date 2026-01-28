package com.roomease.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Entity

public class OauthUser implements UserDetails {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<UserRole> userRole = new HashSet<>();
    @OneToMany(mappedBy = "oauthUser",cascade = CascadeType.ALL)
    private List<Booking> booking;

    public OauthUser(Long id, String sub, String name, String email, String picture, String provider, String password, boolean verified, Set<UserRole> userRole) {
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

    public Set<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<UserRole> userRole) {
        this.userRole = userRole;
    }


    public OauthUser(String sub  ,String name, String email, String picture,String provider,String password,Set<UserRole> userRole) {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.stream()
                .map(r-> new SimpleGrantedAuthority("ROLE_"+r.getRole())).toList();

    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
