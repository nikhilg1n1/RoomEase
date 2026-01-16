package com.roomease.Services;

import com.roomease.Controller.PaymentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final static Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public  void sendOtp(String to, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Verification Code");
        message.setText("your otp is :" + otp);
        mailSender.send(message);
    }
    
    public void ReservedRoomEmail(String to, String userName, String roomTitle, String city, Double rent, Double deposit, String  date){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Room Has Been Successfully Reserved \uD83C\uDF89");
        message.setText(
                "Hi " + userName + ",\n\n" +
                        "Great news! 🎉\n" +
                        "Your room has been successfully reserved on RoomEase.\n\n" +

                        "🏠 Reservation Details\n\n" +
                        "Room: " + roomTitle + "\n" +
                        "Location: " + city + "\n" +
                        "Monthly Rent: ₹" + rent + "\n" +
                        "Deposit Paid: ₹" + deposit + "\n" +
                        "Reservation Date: " + date + "\n\n" +

                        "Your booking is currently confirmed, and the room is reserved under your name.\n" +
                        "The property owner/manager will reach out to you soon with further instructions " +
                        "regarding move-in and documentation.\n\n" +

                        "If you have any questions or need assistance, feel free to reply to this email " +
                        "or contact our support team.\n\n" +

                        "Thank you for choosing RoomEase — we’re excited to help you find your perfect place!\n\n" +

                        "Warm regards,\n" +
                        "Team RoomEase\n" +
                        "📧 support@roomease.com\n" +
                        "🌐 www.roomease.com"
        );
        mailSender.send(message);
        logger.info("Room is reserved and confirmation email has been sent to {}",to);


    }
}
