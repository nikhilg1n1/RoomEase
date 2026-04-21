package com.roomease.Auth;

import com.roomease.Entity.OauthUser;
import com.roomease.Entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {
    @Value("${JWT_SECRET}")
    private String secret;



    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private  Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public  String generateAccessToken(OauthUser user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("roles",user.getUserRole()
                                        .stream()
                                        .map(UserRole::getRole).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000 *60 *15))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validatedToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (Exception e){
            logger.info("Error is occurred here ");
            logger.error("Validated token is failing "+e.getMessage());
//            e.printStackTrace();
        }
        return false;
    }
    public  String generateRefreshToken(OauthUser user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("roles",user.getUserRole()
                        .stream().map(UserRole::getRole).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L *60*60*24*30))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }
    public  String extractEmail(String token){
        try{
            return parseToken(token).getSubject();
        }catch (ExpiredJwtException e){
            logger.info("Token expired ");
            e.printStackTrace();
            return null;
        }
    }
    public  List<String> extractRole(String token){
        try{
            return parseToken(token).get("roles", List.class);
        }catch (ExpiredJwtException e){
            logger.info("Role is Incorrect ");
            e.printStackTrace();
            return null;
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
