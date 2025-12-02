package com.roomease.Repository;

import com.roomease.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo ,Long> {
    public UserInfo findByEmail(String email);
}
