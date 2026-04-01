package com.roomease;

import com.roomease.Auth.JwtFilter;
import com.roomease.Auth.JwtUtils;
import com.roomease.Config.SecurityConfig;
import com.roomease.Controller.RoomController;
import com.roomease.Repository.*;
import com.roomease.Services.ListRoomService;
import com.roomease.Services.RentRoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RoomController.class)
public class RoomSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private RentRoomService rentRoomService;
    @MockitoBean private ListRoomRepo listRoomRepo;
    @MockitoBean private ListRoomService listRoomService;
    @MockitoBean private RoomImageRepo roomImageRepo;
    @MockitoBean private OauthUserRepo oauthUserRepo;
    @MockitoBean private BookingRepo bookingRepo;
    @MockitoBean private JwtUtils jwtUtils;
    @MockitoBean private JwtFilter jwtFilter;
    @MockitoBean private ReviewRepo reviewRepo;

    @Test
    void getAllRooms_withoutAuth_shouldReturn401() throws  Exception{
        mockMvc.perform(get("/v1/rooms"))
                .andExpect(status().is(
                        org.hamcrest.Matchers.either(
                                org.hamcrest.Matchers.is(401)
                        ).or(org.hamcrest.Matchers.is(302))
                ));
    }

    @Test
    void searchRooms_withoutAuth_shouldReturn401() throws  Exception{
        mockMvc.perform(get("/v1/searchRooms").param("query","Pune"))
                .andExpect(status().is(
                        org.hamcrest.Matchers.either(
                                org.hamcrest.Matchers.is(401)
                        ).or(org.hamcrest.Matchers.is(302))
                ));
    }

    @Test
    void getRoomDescription_withoutAuth_shouldReturn401() throws Exception{
        mockMvc.perform(get("/v1/description/1"))
                .andExpect(status().is(
                        org.hamcrest.Matchers.either(
                                org.hamcrest.Matchers.is(401)
                        ).or(org.hamcrest.Matchers.is(302))
                ));
    }

}
