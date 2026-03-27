package com.roomease;

import com.roomease.DTO.RoomCardDto;
import com.roomease.Entity.ListRooms;
import com.roomease.Entity.RoomImage;
import com.roomease.Repository.ListRoomRepo;
import com.roomease.Services.RentRoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class RentRoomServiceTest {
    @Mock
    private ListRoomRepo listRoomRepo;

    @InjectMocks
    private RentRoomService rentRoomService;

    private ListRooms createFakeRoom(Long id , String title, Double rent,String city, boolean hasImage){
        ListRooms room = new ListRooms();
        room.setRoomId(id);
        room.setTitle(title);
        room.setRent(rent);
        room.setCity(city);

        if(hasImage){
            RoomImage image = new RoomImage();
            image.setId(100L);
            room.setRoomImages(List.of(image));
        }else{
            room.setRoomImages(new ArrayList<>());
        }
        return  room;
    }

    //Test for return correct number of rooms
    @Test
    void getDataForRoomCard_shouldReturnAllRooms(){
        List<ListRooms> fakeRooms = List.of(
                createFakeRoom(1L,"1 title",8000.0,"pune",true),
                createFakeRoom(1L,"2 title",8999.0,"dubai",true)

        );
        when(listRoomRepo.findAll()).thenReturn(fakeRooms);

        List<RoomCardDto> result = rentRoomService.getDataForRoomCard();
        assertEquals(2,result.size());
    }

    //Rooms with no image returns null imageId
    @Test
    void getDataForRoomCard_roomWithNoImage_shouldReturnNullImageId(){
        ListRooms roomWithNoImage = createFakeRoom(1L, "Empty Room", 5000.0,"goa",false);
        when(listRoomRepo.findAll()).thenReturn(List.of(roomWithNoImage));
        List<RoomCardDto> result = rentRoomService.getDataForRoomCard();
        assertNull(result.get(0).getImageId());
    }
}
