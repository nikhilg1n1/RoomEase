package com.roomeaseauth.Controller;

import com.roomeaseauth.Config.RedisConfig;
import com.roomeaseauth.DTO.UserDataCache;
import com.roomeaseauth.Services.CachedUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final RedisTemplate<String ,Object> template;
    private final CachedUserService cachedUserService;

    public Oauth2LoginSuccessHandler(RedisTemplate<String, Object> template, CachedUserService cachedUserService) {
        this.template = template;
        this.cachedUserService = cachedUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String id = oAuth2User.getName();
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        String picture = (String) oAuth2User.getAttributes().get("picture");

        UserDataCache userDataCache = new UserDataCache(id,name,email,picture);
        cachedUserService.saveUser(userDataCache);
//        response.sendRedirect("localhost:5173/");

      }






}
