package com.roomeaseauth.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomeaseauth.Entity.OauthUser}
 */
@Value
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