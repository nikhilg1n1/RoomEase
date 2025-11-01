package com.roomeaseauth.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/v1/users")
public class AuthController {

    @GetMapping("/user")
    public OidcUser getUser(@AuthenticationPrincipal OidcUser user){
        System.out.println("User Details : " + user);
        return user;

    }
    //Sending the User data to frontend after the login successful
    @GetMapping("/info")
    public Map<String,Object> getLoggedInUser(OAuth2AuthenticationToken authenticationToken){
        if (authenticationToken == null){
            return Map.of("authenticated",false);
        }
        Map<String,Object> userDetails = authenticationToken.getPrincipal().getAttributes();
        return Map.of(
                "authenticated",true,
                "name",userDetails.get("name"),
                "email",userDetails.get("email"),
                "picture",userDetails.get("picture")
        );

    }

}
