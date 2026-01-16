package com.roomease.Services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.lettuce.core.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class RazorPayService {
    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @Value("${razorpay.key}")
    private String razorpayKey;

    public Order createOrder(Double amount) {
        try{
            RazorpayClient client = new RazorpayClient(
                    razorpayKey,razorpaySecret
            );
            JSONObject options = new JSONObject();
            options.put("amount",amount.intValue()*100);
            options.put("currency","INR");
            options.put("receipt","txn_" + System.currentTimeMillis());

            Order order = client.orders.create(options);
            return order;

        }catch (RazorpayException e){
            throw new RuntimeException("Failed to create Razorpay order",e);
        }
    }

    public String hmacsha256(String data , String secret ) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKey);
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : rawHmac) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e){
            throw new RuntimeException("Failed to generate hmac",e);
        }
    }

}
