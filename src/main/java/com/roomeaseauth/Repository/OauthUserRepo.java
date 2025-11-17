package com.roomeaseauth.Repository;

import com.roomeaseauth.Entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthUserRepo extends JpaRepository<OauthUser, Long> {
    Optional<OauthUser> findBySub(String s);
}
