package com.roomease;

import com.roomease.Auth.JwtUtils;
import com.roomease.Entity.OauthUser;
import com.roomease.Entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;

public class JwtUtilsTest {
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp(){
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils , "secret","test-secret-key-only-for-unit-testing-not-real-1234567890abcdef");
        jwtUtils.init();
    }

    private OauthUser createFakeUser(){
        Set<UserRole> roles = new HashSet<>();
        UserRole role = new UserRole(1L,"user");
        roles.add(role);
        OauthUser oauthUser = new OauthUser();
        oauthUser.setEmail("testUserMail@gmail.com");
        oauthUser.setUserRole(roles);

        return oauthUser;
    }

    //Test for accessToken
    @Test
    void generateAccessToken_shouldReturnNonNullToken(){
        String token = jwtUtils.generateAccessToken(createFakeUser());
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    //Test for extraction of mail
    @Test
    void extractMailFromToken_ShouldReturnCorrectMail(){
        OauthUser user = createFakeUser();
        String token = jwtUtils.generateAccessToken(user);
        String mail = jwtUtils.extractEmail(token);
        assertEquals("testUserMail@gmail.com",mail);
    }

    //Test for token validation
    @Test
    void validateToken_withValidToken_shouldReturnTrue(){
        OauthUser user = createFakeUser();
        String token = jwtUtils.generateAccessToken(user);
        assertTrue(jwtUtils.validatedToken(token));
    }

    //Test for the Tempered token
    @Test
    void validToken_withTemperedToken_shouldReturnFalse(){
        OauthUser user = createFakeUser();
        String token = jwtUtils.generateAccessToken(user);
        String temperedToken = token + "abcbd";
        assertFalse(jwtUtils.validatedToken(temperedToken));
    }

    //Test ffor checking the refreshToken logic
    @Test
    void validateRefreshToken_withValidRefreshToken_shouldReturnTrue(){
        OauthUser user = createFakeUser();
        String refreshToken = jwtUtils.generateRefreshToken(user);
        assertNotNull(refreshToken);
        assertTrue(jwtUtils.validatedToken(refreshToken));
    }
}
