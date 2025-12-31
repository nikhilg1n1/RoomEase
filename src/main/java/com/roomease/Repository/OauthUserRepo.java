package com.roomease.Repository;

import com.roomease.Entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthUserRepo extends JpaRepository<OauthUser, Long> {
    Optional<OauthUser> findBySub(String s);
    OauthUser findByEmail(String email);
}
