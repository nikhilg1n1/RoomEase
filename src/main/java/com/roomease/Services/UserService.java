package com.roomease.Services;

import com.roomease.Entity.OauthUser;
import com.roomease.Repository.OauthUserRepo;
import com.roomease.Repository.UserInfoRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserInfoRepo userInfoRepo;
    private final OauthUserRepo oauthUserRepo;
    private final CachedUserService cachedUserService;


    public UserService(UserInfoRepo userInfoRepo, OauthUserRepo oauthUserRepo, CachedUserService cachedUserService) {
        this.userInfoRepo = userInfoRepo;
        this.oauthUserRepo = oauthUserRepo;
        this.cachedUserService = cachedUserService;
    }
@Transactional
    public OauthUser saveIfFirstLogin(OauthUser oauthUser) {
        OauthUser user =  oauthUserRepo.findByEmail(oauthUser.getEmail());
        if(user == null){
            System.out.println("Saving first-time Oauth user");
            return oauthUserRepo.save(oauthUser);
        }
        System.out.println("User already exists in Database");
        return user;
    }

}
