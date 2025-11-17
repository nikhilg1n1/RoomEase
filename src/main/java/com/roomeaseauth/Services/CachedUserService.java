package com.roomeaseauth.Services;

import com.roomeaseauth.DTO.UserDataCache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedUserService {

    //saved the user info in redis when the user logs in
    @CachePut(value = "oauthusers",key = "#userDataCache.id")
    public UserDataCache saveUser(UserDataCache userDataCache){
        System.out.println("Saving the user in redis cache " + userDataCache.getEmail());
        return userDataCache;
    }

    @Cacheable(value = "oauthusers",key = "#id",unless = "#result=null")
    public UserDataCache getUser(String id){
        System.out.println("Fetching the user from redis ");
        return  null;

    }

    @CacheEvict(value = "oauthusers", key = "#id")
    public  void removeUser(String id){
        System.out.println("Removing user from Redis Cache ...");
    }
}
