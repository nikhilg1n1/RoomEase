package com.roomease.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomease.Entity.UserRole}
 */

public class UserRoleDto implements Serializable {
    Long id;
    String role;

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}