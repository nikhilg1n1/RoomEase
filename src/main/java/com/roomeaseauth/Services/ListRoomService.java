package com.roomeaseauth.Services;

import com.roomeaseauth.DTO.AmenitiesDto;
import com.roomeaseauth.DTO.ListRoomsDto;
import com.roomeaseauth.Entity.ListRooms;
import com.roomeaseauth.Entity.RoomImage;
import com.roomeaseauth.Repository.ListRoomRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListRoomService {

    private final ListRoomRepo listRoomRepo;

    public ListRoomService(ListRoomRepo listRoomRepo) {
        this.listRoomRepo = listRoomRepo;
    }

    public void saveListedRoom(ListRooms listRooms){

//        if(listRooms.getRoomImages() != null){
//            for(RoomImage image : listRooms.getRoomImages()){
//                image.setListRooms(listRooms);
//            }
//        }

//        if(listRooms.getAmenities() != null){
//            listRooms.getAmenities().forEach(a ->{
//                if(!a.getListRooms().contains(listRooms)){
//                    a.getListRooms().add(listRooms);
//                }
//                });
//        }
        System.out.println(listRooms);
        listRoomRepo.save(listRooms);
    }
    public List<ListRoomsDto> getAllRooms(){
        List<ListRooms> rooms = listRoomRepo.findAll();

        return rooms.stream().map(
                room -> new ListRoomsDto(
                        room.getRoomId(),
                        room.getTitle(),
                        room.getDescription(),
                        room.getRent(),
                        room.getSecurityDeposit(),
                        room.getBeds(),
                        room.isAttachedWashroom(),
                        room.isBalcony(),
                        room.getAddress(),
                        room.getCity(),
                        room.getLandmark(),
                        room.getPhoneNumber(),
                        room.getAlternateNumber(),
                        room.getEmail(),
                        room.getAvailableDate(),
                        room.getRoomImages(),
                        room.getFurnishingType(),
                        room.getOccupacyType(),
                        room.getRoomType(),
                        room.getAmenities()

                )
        ).toList();
    }
}
