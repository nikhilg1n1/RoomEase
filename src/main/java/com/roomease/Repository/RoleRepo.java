package com.roomease.Repository;

import com.roomease.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<UserRole,Long> {
    @Override
    Optional<UserRole> findById(Long aLong);

    Optional<UserRole> findByRole(String role);
}
