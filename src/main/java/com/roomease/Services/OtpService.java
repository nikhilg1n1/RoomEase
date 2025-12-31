package com.roomease.Services;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private  final RedisTemplate<String , String> redisTemplate;
    private final SecureRandom random = new SecureRandom();

    public OtpService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

    public String generateOtp(String email){
        String otp = String.valueOf(100000 + random.nextInt(900000));
        redisTemplate.opsForValue().set(
                "otp :"+email,
                otp,
                5, TimeUnit.MINUTES

        );
        return otp;

    }

    public boolean verifyOtp(String email , String otp){
        String key = "otp :"+email;
        String storedOtp = redisTemplate.opsForValue().get(key);

        if(storedOtp.equals(otp)){
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}
