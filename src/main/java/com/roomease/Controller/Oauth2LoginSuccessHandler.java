package com.roomease.Controller;

import com.roomease.Auth.JwtUtils;
import com.roomease.DTO.UserDataCache;
import com.roomease.Entity.OauthUser;
import com.roomease.Entity.UserRole;
import com.roomease.Repository.OauthUserRepo;
import com.roomease.Repository.RoleRepo;
import com.roomease.Services.CachedUserService;
import com.roomease.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;
@Component
@Slf4j
@CrossOrigin(origins = "https://roomease-iota.vercel.app",allowCredentials = "true")
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${JWT_SECRET}")
    private String secret;

    private static final Logger logger = getLogger(Oauth2LoginSuccessHandler.class);
    private final RoleRepo roleRepo;

    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, Object> template;
    private final CachedUserService cachedUserService;
    private final UserService userService;
    private final OauthUserRepo oauthUserRepo;



    public Oauth2LoginSuccessHandler(RoleRepo roleRepo, JwtUtils jwtUtils, RedisTemplate<String, Object> template, CachedUserService cachedUserService, UserService userService, OauthUserRepo oauthUserRepo) {
        this.roleRepo = roleRepo;
        this.jwtUtils = jwtUtils;
        this.template = template;
        this.cachedUserService = cachedUserService;
        this.userService = userService;
        this.oauthUserRepo = oauthUserRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        logger.info("We are in the Oauth2LoginSuccessHandler");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String id = oAuth2User.getName();
        String sub = (String) oAuth2User.getAttributes().get("sub");
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        String picture = (String) oAuth2User.getAttributes().get("picture");
        String password = "";
        String provider = "GOOGLE";

        OauthUser user = oauthUserRepo.findByEmail(email);
//        UserRole role = roleRepo.findByRole("USER").
//                orElseThrow(()-> new RuntimeException("User role is not found"));

        if(user == null){
            logger.info("New User login through Google");
            UserRole defaultRole = roleRepo.findByRole("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not found"));

            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(defaultRole);
            OauthUser newUser = new OauthUser();
            newUser.setSub(sub);
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setPicture(picture);
            newUser.setProvider(provider);
            newUser.setPassword("");
            newUser.setUserRole(userRoles);

            userService.saveIfFirstLogin(newUser);
            user = newUser;
        }

        List<String> roles = user.getUserRole()
                .stream()
                .map(UserRole::getRole)
                .toList();

//        Set<UserRole> userRoles = new HashSet<>();

        UserDataCache userDataCache = new UserDataCache(id, name, email, picture,provider,password,roles);
        cachedUserService.saveUser(userDataCache);
        logger.info("Provider is - > " + provider);

        logger.info("Creating Cookie for user {}",name);
        String accessToken = jwtUtils.generateAccessToken(user);
        logger.info("Access Token is {}", accessToken);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60 * 24 * 30)
                .build();
        logger.info("Cookies are -> {}",cookie);
        response.addHeader("Set-Cookie",cookie.toString());

        response.sendRedirect("https://roomease-iota.vercel.app");
        return;

    }


}
