package com.roomease.Services;

import com.roomease.Entity.OauthUser;
import com.roomease.Repository.OauthUserRepo;
import com.roomease.Repository.UserInfoRepo;
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

    public OauthUser saveIfFirstLogin(OauthUser oauthUser) {
        return oauthUserRepo.findBySub(oauthUser.getSub())
                .orElseGet(() -> {
                    System.out.println("Saving first-time Oauth user");
                    return oauthUserRepo.save(new OauthUser(
                            oauthUser.getSub(),
                            oauthUser.getName(),
                            oauthUser.getEmail(),
                            oauthUser.getPicture(),
                            oauthUser.getProvider(),
                            oauthUser.getPassword(),
                            oauthUser.getUserRole()
                            ));
                });
    }

}
