package com.roomease.Controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.roomease.DTO.BookingDto;
import com.roomease.Entity.Booking;
import com.roomease.Entity.BookingStatus;
import com.roomease.Entity.ListRooms;
import com.roomease.Entity.OauthUser;
import com.roomease.Repository.BookingRepo;
import com.roomease.Repository.ListRoomRepo;
import com.roomease.Repository.OauthUserRepo;
import com.roomease.Services.EmailService;
import com.roomease.Services.RazorPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
@RestController
@RequestMapping("/v1")
public class PaymentController {
    @Value("${razorpay.key}")
    private String razorpayKey;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    private final BookingRepo bookingRepo;
    private final ListRoomRepo listRoomRepo;
    private final RazorPayService razorPayService;
    private final OauthUserRepo oauthUserRepo;
    private final EmailService emailService;
    private final static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    public PaymentController(BookingRepo bookingRepo, ListRoomRepo listRoomRepo, RazorPayService razorPayService, OauthUserRepo oauthUserRepo, EmailService emailService) {
        this.bookingRepo = bookingRepo;
        this.listRoomRepo = listRoomRepo;
        this.razorPayService = razorPayService;
        this.oauthUserRepo = oauthUserRepo;
        this.emailService = emailService;
    }

    LocalDate today = LocalDate.now();
    String formattedDate = today.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    String email;
    ListRooms room = new ListRooms();
    @PostMapping("/razorpay-booking")
    public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto, Authentication auth){
        try {
            email = (String) auth.getPrincipal();
            logger.info("payment user email is ->{}",email);
            OauthUser user = oauthUserRepo.findByEmail(email);
            if (user == null){
                return  ResponseEntity.status(401).body("User not found");
            }
            room = listRoomRepo.findById(bookingDto.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not Found"));
            double tokenAmount = Math.min(room.getRent() * 0.2 , room.getSecurityDeposit());

            Booking booking = new Booking();
            booking.setOauthUser(user);
            booking.setListRooms(room);
            booking.setRent(room.getRent());
            booking.setDeposit(room.getSecurityDeposit());
            booking.setBookingToken(tokenAmount);
            booking.setStatus(BookingStatus.INITIATED);

            bookingRepo.save(booking);


            Order order = razorPayService.createOrder(booking.getBookingToken());
            return ResponseEntity.ok(Map.of(
                    "bookingId", booking.getId(),
                    "orderId", order.get("id"),
                    "amount", booking.getBookingToken(),
                    "razorpayKey", razorpayKey
            ));
        }catch (Exception e){
            logger.error("Error in payment processing -> {}",e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(404).body("Something went wrong .. ");
        }

    }

    @PostMapping("/payments/verify")
    public ResponseEntity<?>verifyPayment(@RequestBody Map<String,String> body){

        try{
            String razorpayOrderId = body.get("razorpayOrderId");
            String razorpayPaymentId = body.get("razorpayPaymentId");
            String razorpaySignature = body.get("razorpaySignature");
            Long bookingId = Long.valueOf(body.get("bookingId"));
            logger.info("Verifying the payment for booking id -> "+ bookingId);
            logger.info("OrderId from Razorpay -> {}", razorpayOrderId);
            logger.info("PaymentId from Razorpay -> {}", razorpayPaymentId);
            logger.info("Signature from Razorpay -> {}", razorpaySignature);

            Booking booking = bookingRepo.findById(bookingId)
                    .orElseThrow(()-> new RuntimeException("Booking not found"));

            String payload = razorpayOrderId + "|" + razorpayPaymentId;
            String generatedSignature = razorPayService.hmacsha256(payload,razorpaySecret);
            logger.info("Generated Signature -> {}", generatedSignature);


            if(!generatedSignature.equals(razorpaySignature)){
                booking.setStatus(BookingStatus.FAILED);
                bookingRepo.save(booking);
                return ResponseEntity.status(400).body("Invalid payment signature");

            }
            booking.setStatus(BookingStatus.RESERVED);
            bookingRepo.save(booking);
            emailService.ReservedRoomEmail(email,email,room.getTitle(),room.getCity(),room.getRent(),room.getSecurityDeposit(),formattedDate);


            return ResponseEntity.ok(Map.of(
                    "status","success",
                    "bookingId",booking.getId()
            ));

        }catch (Exception e){
            e.printStackTrace();
            logger.error("Error in verifying the payment -> {}", e.getMessage());
            return ResponseEntity.status(500).body("payment verifcation failed");
        }
    }

}
