package com.roomease.Services;

import com.roomease.Controller.AuthController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private final static Logger logger = LoggerFactory.getLogger(OtpService.class);

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

        if(storedOtp == null){
            logger.info("Otp is not found or expired for this mail : {}", email);
            return false;

        }
        logger.info("Stored Otp is ->" + storedOtp);

        if(storedOtp.equals(otp)){
            redisTemplate.delete(key);
            return true;
        }
        return false;
    }
}
