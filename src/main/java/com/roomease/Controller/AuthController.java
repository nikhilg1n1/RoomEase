package com.roomease.Controller;

import com.roomease.Auth.JwtUtils;
import com.roomease.DTO.UserDataCache;
import com.roomease.Services.CachedUserService;
import com.roomease.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/v1/users")
public class AuthController {

    private final CachedUserService cachedUserService;

    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private  final JwtUtils jwtUtils;
    private final UserService userService;

    public AuthController(CachedUserService cachedUserService, JwtUtils jwtUtils, UserService userService) {
        this.cachedUserService = cachedUserService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
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
                "name",user.getName(),
                "email",user.getEmail(),
                "picture",user.getPicture()

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

                String accessToken = jwtUtils.generateAccessToken(email);
                logger.info("Refresh Token is {}",accessToken);

                return ResponseEntity.ok(Map.of("access_token",accessToken));

            }
        }
        logger.info("Refresh cookie is not found");
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/logout/{id}")
    public ResponseEntity<?> logOutUser(@PathVariable  String id, HttpServletResponse response , HttpServletRequest request){
        cachedUserService.removeUser(id);
        try {
            request.logout();
        }
        catch (ServletException e){
            e.printStackTrace();
        }
        HttpSession session = request.getSession(false);
        if(session != null) session.invalidate();

        return ResponseEntity.ok("Logout successful");
    }

}
