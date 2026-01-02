package com.roomease.Services;

import com.roomease.DTO.RoomCardDto;
import com.roomease.DTO.RoomFilterDto;
import com.roomease.Entity.ListRooms;
import com.roomease.Repository.ListRoomRepo;
import com.roomease.Repository.RoomSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentRoomService {

    private final ListRoomRepo listRoomRepo;

    public RentRoomService(ListRoomRepo listRoomRepo) {
        this.listRoomRepo = listRoomRepo;
    }

    public List<RoomCardDto> getDataForRoomCard(){

        List<ListRooms> rooms = listRoomRepo.findAll();

        return rooms.stream().map( room ->
                new RoomCardDto(
                        room.getRoomId(),
                        room.getTitle(),
                        room.getRent(),
                        room.getCity(),
                        room.getRoomImages().isEmpty()?
                                null : room.getRoomImages().get(0).getId()
                )
        ).toList();

    }

    public List<RoomCardDto> filter(RoomFilterDto roomFilterDto){

        List<ListRooms> rooms = listRoomRepo.findAll(RoomSpecification.apply(roomFilterDto));

        return rooms.stream().map(room ->
                new RoomCardDto(
                        room.getRoomId(),
                        room.getTitle(),
                        room.getRent(),
                        room.getCity(),
                        room.getRoomImages().isEmpty() ?
                                null : room.getRoomImages().get(0).getId()

                )


        ).toList();
    }


}
