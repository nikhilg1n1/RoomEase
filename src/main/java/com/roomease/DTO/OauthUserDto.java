package com.roomease.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomease.Entity.OauthUser}
 */
public class OauthUserDto implements Serializable {
    String id;
    String name;
    String email;
    String picture;

    public OauthUserDto(String id, String name, String email, String picture) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public OauthUserDto() {
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}