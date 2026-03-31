package com.roomease;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomease.Auth.JwtFilter;
import com.roomease.Auth.JwtUtils;
import com.roomease.Config.SecurityConfig;
import com.roomease.Controller.RoomController;
import com.roomease.DTO.ListRoomsDto;
import com.roomease.DTO.RoomCardDto;
import com.roomease.DTO.RoomImageDto;
import com.roomease.Repository.*;
import com.roomease.Services.ListRoomService;
import com.roomease.Services.RentRoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoBeans;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

@WebMvcTest(value = RoomController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean private RentRoomService rentRoomService ;
    @MockitoBean private JwtUtils jwtUtils;
    @MockitoBean private JwtFilter jwtFilter;
    @MockitoBean private ListRoomService listRoomService;
    @MockitoBean private RoomImageRepo roomImageRepo;
    @MockitoBean private ListRoomRepo listRoomRepo;
    @MockitoBean private BookingRepo bookingRepo;
    @MockitoBean private OauthUserRepo oauthUserRepo;

    @MockitoBean private ReviewRepo reviewRepo;
    //We are creating the fake RoomCardDto
    private RoomCardDto fakeRoomCard(Long id,String title,Double rent,String city,String address,Long image){
        return new RoomCardDto(id,title,rent,city,address,image);
    }
    @Test
    @WithMockUser
    void getAllRooms_shouldReturn200WithRoomList() throws Exception {
        List<RoomCardDto> fakeRooms = List.of(
                fakeRoomCard(1L,"Rooms For Boys",3000.0,"Pune","abc123",1L),
                fakeRoomCard(1L,"Rooms For girls",3000.0,"Pune","123654",1L)
        );
        when(rentRoomService.getDataForRoomCard()).thenReturn(fakeRooms);
        mockMvc.perform(get("/v1/rooms").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].title",is("Rooms For Boys")))
                .andExpect(jsonPath("$[0].city",is("Pune")))
                .andExpect(jsonPath("$[1].title",is("Rooms For girls")));
    }

    @Test
    @WithMockUser
    void getAllRooms_noRooms_shouldReturnEmptyArray() throws Exception{
        when(rentRoomService.getDataForRoomCard()).thenReturn(List.of());

        mockMvc.perform(get("/v1/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    @WithMockUser
    void getAllRooms_shouldCallServiceExactlyOnce() throws Exception{
        when(rentRoomService.getDataForRoomCard()).thenReturn(List.of());

        mockMvc.perform(get("/v1/rooms").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(rentRoomService,times(1)).getDataForRoomCard();

    }

    @Test
    @WithMockUser
    void getAllRooms_withMockUser_shouldReturn200()throws Exception{
        when(rentRoomService.getDataForRoomCard()).thenReturn(List.of());
        mockMvc.perform(get("/v1/rooms")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getRoomDescription_validId_shouldReturn200() throws  Exception{
        ListRoomsDto roomsDto = new ListRoomsDto();
        roomsDto.setRoomId(1L);
        roomsDto.setTitle("Rooms For Boys");
        roomsDto.setRent(5000.0);
        roomsDto.setCity("Pune");
        when(listRoomService.getRoomDescription(1L)).thenReturn(roomsDto);

        mockMvc.perform(get("/v1/description/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId",is(1)))
                .andExpect(jsonPath("$.title",is("Rooms For Boys")))
                .andExpect(jsonPath("$.city",is("Pune")));

    }

    @Test
    @WithMockUser
    void getRoomDescription_shouldCallServiceWithCorrectId() throws  Exception{
        when(listRoomService.getRoomDescription(5L)).thenReturn(null);

        mockMvc.perform(get("/v1/description/5")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(listRoomService,times(1)).getRoomDescription(5L);
    }
    @Test
    @WithMockUser
    void getRoomDescription_inValidId_shouldReturn404() throws  Exception{
        when(listRoomService.getRoomDescription(999999L)).thenReturn(null);

        mockMvc.perform(get("/v1/description/999999")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void searchRooms_withValidQuery_ShouldReturnResults() throws  Exception{
        List<RoomCardDto> results = List.of(
                fakeRoomCard(1l,"Room in Pune",8000.0,"Pune","abc123",1L)
        );
        when(rentRoomService.searchRooms("Pune")).thenReturn(results);

        mockMvc.perform(get("/v1/searchRooms")
                        .param("query","Pune")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].city",is("Pune")))
                .andExpect(jsonPath("$[0].title",is("Room in Pune")));
    }

    @Test
    @WithMockUser
    void searchRooms_noMatch_shouldReturnEmptyArray() throws Exception{
        when(rentRoomService.searchRooms("xyz123")).thenReturn(List.of());

        mockMvc.perform(get("/v1/searchRooms")
                        .param("query","xyz123")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(0)));
    }

    @Test
    @WithMockUser
    void searchRooms_shouldCallServiceCorrectQuery() throws Exception{
        when(rentRoomService.searchRooms("Pune")).thenReturn(List.of());

        mockMvc.perform(get("/v1/searchRooms")
                .param("query","Pune")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(rentRoomService,times(1)).searchRooms("Pune");
    }

}
