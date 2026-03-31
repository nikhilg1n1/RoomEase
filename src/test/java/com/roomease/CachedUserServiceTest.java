package com.roomease;

import com.roomease.DTO.UserDataCache;
import com.roomease.Services.CachedUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CachedUserServiceTest {
    @Mock
    private RedisTemplate<String,Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private CachedUserService cachedUserService;

    private UserDataCache fakeUser;

    @BeforeEach
    void setUp(){
        List<String> role = new ArrayList<>();
        role.add("user");
        fakeUser = new UserDataCache("testUserMail@gmail.com",role,"Google",null);
    }

    //Test for saving the user in redis
    @Test
    void saveUser_shouldStoreUserInRedis(){
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        cachedUserService.saveUser(fakeUser);
        verify(valueOperations,times(1)).set("testUserMail@gmail.com",fakeUser);
    }

    //TEst to get the cached User from Redis
    @Test
    void getCachedUser_shouldReturnTrue(){
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("testUserMail@gmail.com")).thenReturn(fakeUser);
        UserDataCache user = cachedUserService.getUser("testUserMail@gmail.com");
        assertNotNull(user);
        assertEquals("testUserMail@gmail.com",user.getEmail());
    }

    //Test for if the user not found
    @Test
    void getUser_notfound_shouldReturnNull(){
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("abc@gmail.com")).thenReturn(null);

        UserDataCache user = cachedUserService.getUser("abc@gmail.com");
        assertNull(user);
    }

    //Test for delete user from redis
    @Test
    void removeUser_shouldDeleteUserRedis(){
        cachedUserService.removeUser("testUserMail@gmail.com");
        verify(redisTemplate,times(1)).delete("testUserMail@gmail.com");

    }
}

