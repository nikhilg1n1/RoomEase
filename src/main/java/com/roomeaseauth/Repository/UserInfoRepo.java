package com.roomeaseauth.Repository;

import com.roomeaseauth.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo ,Long> {
    public UserInfo findByEmail(String email);
}
