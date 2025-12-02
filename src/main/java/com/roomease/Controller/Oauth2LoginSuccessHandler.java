package com.roomease.Controller;

import com.roomease.Auth.JwtUtils;
import com.roomease.DTO.UserDataCache;
import com.roomease.Entity.OauthUser;
import com.roomease.Services.CachedUserService;
import com.roomease.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getILoggerFactory;
import static org.slf4j.LoggerFactory.getLogger;
@Component
@Slf4j
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${secret}")
    private String secret;

    private static final Logger logger = getLogger(Oauth2LoginSuccessHandler.class);


    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, Object> template;
    private final CachedUserService cachedUserService;
    private final UserService userService;



    public Oauth2LoginSuccessHandler(JwtUtils jwtUtils, RedisTemplate<String, Object> template, CachedUserService cachedUserService, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.template = template;
        this.cachedUserService = cachedUserService;
        this.userService = userService;
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

        UserDataCache userDataCache = new UserDataCache(id, name, email, picture);
        cachedUserService.saveUser(userDataCache);
        OauthUser user = new OauthUser(sub, name, email, picture);
        userService.saveIfFirstLogin(user);

        logger.info("Creating Cookie for user {}",name);


        String accessToken = jwtUtils.generateAccessToken(email);

        logger.info("Access Token is {}", accessToken);


        String refreshToken = jwtUtils.generateRefreshToken(email);

        ResponseCookie cookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60 * 24 * 30)
                .build();
        logger.info("Cookies are -> {}",cookie);
        response.addHeader("Set-Cookie",cookie.toString());

        response.sendRedirect("http://localhost:5173/");
        return;

    }


}
