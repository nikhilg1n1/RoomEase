package com.roomease.Auth;

import com.roomease.Entity.OauthUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
@Component
public class JwtUtils {
    @Value("${secret}")
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
                .claim("role",user.getUserRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000 *60 *15))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public  String generateRefreshToken(OauthUser user){
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role",user.getUserRole())
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

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
