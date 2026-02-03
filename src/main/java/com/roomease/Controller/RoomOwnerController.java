package com.roomease.Controller;

import com.roomease.Auth.JwtUtils;
import com.roomease.DTO.ListRoomsDto;
import com.roomease.DTO.UserDataCache;
import com.roomease.Entity.ListRooms;
import com.roomease.Entity.OauthUser;
import com.roomease.Entity.UserRole;
import com.roomease.Repository.ListRoomRepo;
import com.roomease.Repository.OauthUserRepo;
import com.roomease.Repository.RoleRepo;
import com.roomease.Services.CachedUserService;
import com.roomease.Services.ListRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/v1")
public class RoomOwnerController {

    private final  ListRoomRepo listRoomRepo;

    private final ListRoomService listRoomService;
    private final OauthUserRepo oauthUserRepo;
    private final CachedUserService cachedUserService;
    private final RoleRepo roleRepo;

    private static final Logger logger = LoggerFactory.getLogger(RoomOwnerController.class);



    public RoomOwnerController(ListRoomRepo listRoomRepo, ListRoomService listRoomService, OauthUserRepo oauthUserRepo, CachedUserService cachedUserService, RoleRepo roleRepo) {
        this.listRoomRepo = listRoomRepo;
        this.listRoomService = listRoomService;
        this.oauthUserRepo = oauthUserRepo;
        this.cachedUserService = cachedUserService;

        this.roleRepo = roleRepo;

    }

    @PostMapping("/role/request-owner")
    public ResponseEntity<?> addRole(Authentication auth){
        String email = (String) auth.getPrincipal();

        OauthUser user = oauthUserRepo.findByEmail(email);
        if(user == null){
            return ResponseEntity.status(401).body("user not found");
        }

        UserRole role = roleRepo.findByRole("OWNER").
                orElseThrow(() -> new RuntimeException("OWNER role is not found"));


        Set<UserRole> roles = new HashSet<>(user.getUserRole());
        roles.add(role);
        user.setUserRole(roles);

        System.out.println("The role is ->" + user.getUserRole());

        boolean isOwner = roles.stream()
                        .anyMatch(r->"OWNER".equals(r.getRole()));
        if(isOwner){
            return ResponseEntity.badRequest().body("User already an Owner");
        }
        List<String> userRoles = user.getUserRole()
                .stream()
                .map(UserRole::getRole)
                .toList();

        roles.add(role);
        oauthUserRepo.save(user);
        cachedUserService.saveUser(
                new UserDataCache(
                        user.getEmail(),
                        userRoles,
                        user.getProvider(),
                        user.getPicture()
                )
        );

        return ResponseEntity.ok(Map.of(
                "roles",user.getUserRole().stream().map(UserRole::getRole).toList()
        ));
    }

    @GetMapping("/owner/rooms")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<ListRoomsDto>>getOwnerRooms(Authentication auth){
        String email = (String) auth.getPrincipal();
        logger.info("Fetching the rooms for owner and email is -> " + email);

        OauthUser user = oauthUserRepo.findByEmail(email);

        List<ListRoomsDto> rooms = listRoomService.getAllRoomsForOwner(user);

        logger.info("Rooms of owner is -> "  +rooms.size());


        return ResponseEntity.ok(rooms);
    }
}
