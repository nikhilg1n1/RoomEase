package com.roomease;

import com.roomease.Services.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OtpServiceTest {

    @Mock
    private RedisTemplate<String , String> redisTemplate;

    @Mock
    private ValueOperations<String,String> valueOperations;

    @InjectMocks
    private OtpService otpService;

    @BeforeEach
    void setup(){
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void generateOtp_shouldReturnDigitOtp(){
        String otp = otpService.generateOtp("test@gmail.com");

        assertNotNull(otp);
        assertEquals(6,otp.length());
        assertTrue(otp.matches("\\d{6}"));
    }

    @Test
    void generate_shouldSaveOtpInRedisWithTTL(){
        String mail = "testMail@gmail.com";
        otpService.generateOtp(mail);
        verify(valueOperations, times(1))
                .set(eq("otp :" + mail), anyString(), eq(5L), eq(TimeUnit.MINUTES));
    }
    //TEst for the verifyOtp method of OtpService
    @Test
    void verifyOtp_withCorrectOtp_shouldReturnTrue(){
        String mail = "testMail@gmail.com";
        String otp = "123456";

        when(valueOperations.get("otp :" + mail)).thenReturn(otp);
        boolean result =  otpService.verifyOtp(mail,otp);

        assertTrue(result);
    }

    // Testing with wrong otp
    @Test
    void verifyOtp_withExpiredOtp_shouldReturnFalse(){
        String mail = "testMail@gmail.com";

        when(valueOperations.get("otp :" +mail)).thenReturn("123456");
        boolean result = otpService.verifyOtp(mail,"999999");
        assertFalse(result);
    }

    // test 6: Valid otp deleted from redis after verification
    @Test
    void verifyOtp_afterSuccess_shouldDeleteOtpFromRedis(){
        String mail = "testMail@gmail.com";
        String otp = "123456";
        String key = "otp :" + mail;

        when(valueOperations.get(key)).thenReturn(otp);

        otpService.verifyOtp(mail,otp);

        verify(redisTemplate,times(1)).delete(key);
    }
}
