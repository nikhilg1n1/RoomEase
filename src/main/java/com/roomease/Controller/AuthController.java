package com.roomease.Controller;

import com.roomease.Auth.JwtUtils;
import com.roomease.DTO.UserDataCache;
import com.roomease.Entity.OauthUser;
import com.roomease.Entity.UserRole;
import com.roomease.Repository.OauthUserRepo;
import com.roomease.Repository.RoleRepo;
import com.roomease.Services.CachedUserService;
import com.roomease.Services.EmailService;
import com.roomease.Services.OtpService;
import com.roomease.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/v1/users")
public class AuthController {

    private final CachedUserService cachedUserService;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final OauthUserRepo oauthUserRepo;
    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private  final JwtUtils jwtUtils;
    private final UserService userService;
    private final EmailService emailService;

    public AuthController(CachedUserService cachedUserService, OtpService otpService, PasswordEncoder passwordEncoder, RoleRepo roleRepo, OauthUserRepo oauthUserRepo, JwtUtils jwtUtils, UserService userService, EmailService emailService) {
        this.cachedUserService = cachedUserService;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.oauthUserRepo = oauthUserRepo;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.emailService = emailService;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserDataCache> getCachedUser(@PathVariable String email){
//        UserDataCache user = cachedUserService.getUser(email);
//        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
//
//    }
    //Sending the User data to frontend after the login successful
    @GetMapping("/info")
    public ResponseEntity<?>getLoggedInUser(@RequestHeader ("Authorization")  String authHeader){

        logger.info("Authorization is {}",authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(401).body( Map.of("authenticated",false));
        }

        System.out.println("Authorization is :" + authHeader);

        String token =  authHeader.substring(7);

//        logger.info("Token is {}",token);
        System.out.println("Token is :" + token);

        String email = jwtUtils.extractEmail(token);
        System.out.println("User saved in DB ");

        UserDataCache user = cachedUserService.getUser(email);

        if (user == null){
            return ResponseEntity.status(401).body(Map.of("authenticated",false));
        }

        return ResponseEntity.ok(Map.of(
                "authenticated",true,
                "sub", user.getId(),
                "name",user.getName(),
                "email",user.getEmail(),
                "picture",user.getPicture(),
                "role",user.getRole()

        ));
    }

    @GetMapping("/auth/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null){
            logger.info("Refresh called no cookies present ");
            return  ResponseEntity.status(401).build();
        }
        for(Cookie cookie : request.getCookies()){
            logger.info("Received cookie is {}={}",cookie.getName(),cookie.getValue());

            if (cookie.getName().equals("refresh_token")){
                String email = jwtUtils.extractEmail(cookie.getValue());
                OauthUser user = oauthUserRepo.findByEmail(email);
                String accessToken = jwtUtils.generateAccessToken(user);
                logger.info("Refresh Token is {}",accessToken);

                return ResponseEntity.ok(Map.of("access_token",accessToken));

            }
        }
        logger.info("Refresh cookie is not found");
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/logout/{email}")
    public ResponseEntity<?> logOutUser(@PathVariable  String email, HttpServletResponse response , HttpServletRequest request){
        logger.info("Logging out User");
        cachedUserService.removeUser(email);
        try {
            request.logout();
        }
        catch (ServletException e){
            e.printStackTrace();
        }
        HttpSession session = request.getSession(false);
        if(session != null) session.invalidate();
        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);  // <-- delete cookie
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String,String> req){
//        try{

            String email = req.get("email");
            String otp = otpService.generateOtp(email);
            emailService.sendOtp(email,otp);
//            System.out.println("otp : " + otp);
            logger.info("OTP is :" + otp);
            return ResponseEntity.ok("Otp Sent Successfully");
//        }
//        catch (Exception e){
//            logger.error("Error while sending otp :" + e.getMessage());
//            return ResponseEntity.status(404).body("Something went wrong");
//        }

    }
    @PostMapping("/auth/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        String otp = body.get("otp");

        if (!otpService.verifyOtp(email, otp)) {
            return ResponseEntity.status(401).body("Invalid or expired OTP");
        }

        // Fetch or create user
        OauthUser user = oauthUserRepo.findByEmail(email);
        if(user == null){
            user = new OauthUser();
            user.setEmail(email);
            user.setProvider("Local");
            user.setVerified(true);
            user.setPassword(passwordEncoder.encode(password));
            UserRole role = roleRepo.findByRole("user").
                    orElseThrow(()-> new RuntimeException("User role is not found"));
            user.setUserRole(role);
            userService.saveIfFirstLogin(user);
            logger.info("Normal user saved in DB");

        }

//        String token = jwtUtils.generateAccessToken(user);

//        return ResponseEntity.ok(Map.of(
//                "access_token", token,
//                "user", user
//        ));
        return ResponseEntity.ok("Email verified successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody Map<String,String> body,HttpServletResponse response){
        String email = body.get("email");
        String password = body.get("password");
        UserDataCache userDataCache = cachedUserService.getUser(email);
        OauthUser user = oauthUserRepo.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password,user.getPassword())){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentials");

        }

        String accessToken = jwtUtils.generateAccessToken(user);
        String refreshToken = jwtUtils.generateRefreshToken(user);

        ResponseCookie cookie = ResponseCookie.from("refresh_token",refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(60 * 60 * 24 * 30)
                .build();
        logger.info("Cookies are -> {}",cookie);
        response.addHeader("Set-Cookie",cookie.toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(Map.of(
                        "authenticated",true,
                        "access_token",accessToken,
                        "user",user
                        ));

    }



}
