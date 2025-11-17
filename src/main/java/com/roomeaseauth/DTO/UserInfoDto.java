package com.roomeaseauth.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomeaseauth.Entity.UserInfo}
 */
@Value
public class UserInfoDto implements Serializable {
    Long id;
    String name;
    String email;
    String password;


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}