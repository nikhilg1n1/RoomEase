package com.roomeaseauth.Controller;

import com.roomeaseauth.DTO.UserDataCache;
import com.roomeaseauth.DTO.UserInfoDto;
import com.roomeaseauth.Entity.OauthUser;
import com.roomeaseauth.Services.CachedUserService;
import com.roomeaseauth.Services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.HttpResource;

import java.util.Map;
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/v1/users")
public class AuthController {

    private final CachedUserService cachedUserService;
    private final UserService userService;

    public AuthController(CachedUserService cachedUserService, UserService userService) {
        this.cachedUserService = cachedUserService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDataCache> getCachedUser(@PathVariable String id){
        UserDataCache user = cachedUserService.getUser(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();

    }
    //Sending the User data to frontend after the login successful
    @GetMapping("/info")
    public Map<String,Object> getLoggedInUser(OAuth2AuthenticationToken authenticationToken){
        if (authenticationToken == null){
            return Map.of("authenticated",false);
        }
        Map<String,Object> userDetails = authenticationToken.getPrincipal().getAttributes();

        String sub = (String) userDetails.get("sub");
        String name = (String) userDetails.get("name");
        String email = (String) userDetails.get("email");
        String picture = (String) userDetails.get("picture");

        UserDataCache userCache = new UserDataCache(sub,name,email,picture);
        OauthUser oauthUser = new OauthUser(sub,name,email,picture);
        userService.saveIfFirstLogin(oauthUser);
        cachedUserService.saveUser(userCache);
        System.out.println("User saved in DB ");

        return Map.of(
                "authenticated",true,
                "id",userCache.getId(),
                "name",userCache.getName(),
                "email",userCache.getEmail(),
                "picture",userCache.getPicture()

        );

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
