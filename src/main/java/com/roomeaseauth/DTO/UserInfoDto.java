package com.roomeaseauth.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomeaseauth.Entity.UserInfo}
 */
@Value
public class UserInfoDto implements Serializable {
    Long id;
    String email;
}