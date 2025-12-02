package com.roomease.Services;

import com.roomease.DTO.UserDataCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CachedUserService {
    private final RedisTemplate<String,Object>  redisTemplate;

    public CachedUserService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //saved the user info in redis when the user logs in
//    @CachePut(value = "oauthusers",key = "#userDataCache.id")
    public UserDataCache saveUser(UserDataCache userDataCache){
        System.out.println("Saving the user in redis cache " + userDataCache.getEmail());
        redisTemplate.opsForValue().set(userDataCache.getEmail(),userDataCache);
        return userDataCache;
    }

//    @Cacheable(value = "oauthusers",key = "#email",unless = "#result=null")
    public UserDataCache getUser(String email){
        System.out.println("Fetching the user from redis " + email) ;
        return (UserDataCache) redisTemplate.opsForValue().get(email);

    }

//    @CacheEvict(value = "oauthusers", key = "#id")
    public  void removeUser(String id){
        redisTemplate.delete(id);
        System.out.println("Removing user from Redis Cache ...");
    }
}
