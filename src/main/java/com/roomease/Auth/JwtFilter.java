package com.roomease.Auth;

import com.roomease.DTO.UserDataCache;
import com.roomease.Services.CachedUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CachedUserService cachedUserService;

    public JwtFilter(JwtUtils jwtUtils, CachedUserService cachedUserService) {
        this.jwtUtils = jwtUtils;
        this.cachedUserService = cachedUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header =request.getHeader("Authorization");
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = header.substring(7);

        String email = jwtUtils.extractEmail(token);
        if(email == null){
            filterChain.doFilter(request,response);
            return;
        }
        UserDataCache user = cachedUserService.getUser(email);
        if (user == null){
            filterChain.doFilter(request,response);
            return;
        }
         UsernamePasswordAuthenticationToken authentication =
                 new UsernamePasswordAuthenticationToken(
                         user,
                         null,
                         Collections.emptyList()

                 );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);

    }
}
