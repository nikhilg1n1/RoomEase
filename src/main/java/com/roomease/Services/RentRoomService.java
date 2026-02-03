package com.roomease.Services;

import com.roomease.DTO.RoomCardDto;
import com.roomease.DTO.RoomFilterDto;
import com.roomease.Entity.ListRooms;
import com.roomease.Entity.Review;
import com.roomease.Repository.ListRoomRepo;
import com.roomease.Repository.ReviewRepo;
import com.roomease.Repository.RoomSpecification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RentRoomService {

    private final ListRoomRepo listRoomRepo;
    private final ReviewRepo reviewRepo;

    public RentRoomService(ListRoomRepo listRoomRepo, ReviewRepo reviewRepo) {
        this.listRoomRepo = listRoomRepo;
        this.reviewRepo = reviewRepo;
    }

    public List<RoomCardDto> getDataForRoomCard(){

        List<ListRooms> rooms = listRoomRepo.findAll();

        return rooms.stream().map( room ->
                new RoomCardDto(
                        room.getRoomId(),
                        room.getTitle(),
                        room.getRent(),
                        room.getCity(),
                        room.getAddress(),
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
                        room.getAddress(),
                        room.getRoomImages().isEmpty() ?
                                null : room.getRoomImages().get(0).getId()

                )


        ).toList();
    }

    public List<RoomCardDto> searchRooms(String query ){
        List<ListRooms> searchedRooms = listRoomRepo.searchByCityOrAddress(query);

        return searchedRooms.stream().map(rooms->
                new RoomCardDto(
                        rooms.getRoomId(),
                        rooms.getTitle(),
                        rooms.getRent(),
                        rooms.getCity(),
                        rooms.getAddress(),
                        rooms.getRoomImages().isEmpty()?
                                null : rooms.getRoomImages().get(0).getId()
                )
        ).toList();
    }

    public void updateRoomReview(ListRooms rooms){
        List<Review> review = reviewRepo.findByListRooms(rooms);

        double avg = review.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        rooms.setAverageRating(avg);
        rooms.setTotalRating(review.size());

        listRoomRepo.save(rooms);
    }

}
