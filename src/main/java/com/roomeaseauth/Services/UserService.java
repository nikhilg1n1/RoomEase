package com.roomeaseauth.Services;

import com.roomeaseauth.Entity.OauthUser;
import com.roomeaseauth.Repository.OauthUserRepo;
import com.roomeaseauth.Repository.UserInfoRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
                            oauthUser.getPicture()
                    ));
                });
    }

}
