package com.roomeaseauth.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomeaseauth.DTO.ListRoomsDto;
import com.roomeaseauth.DTO.RoomCardDto;
import com.roomeaseauth.DTO.RoomFilterDto;
import com.roomeaseauth.Entity.ListRooms;
import com.roomeaseauth.Entity.RoomImage;
import com.roomeaseauth.Repository.RoomImageRepo;
import com.roomeaseauth.Services.ListRoomService;
import com.roomeaseauth.Services.RentRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/v1")
public class RoomController {
    private final ListRoomService listRoomService;

    private final RoomImageRepo roomImageRepo;

    private final RentRoomService rentRoomService;

    public RoomController(ListRoomService listRoomService, RoomImageRepo roomImageRepo, RentRoomService rentRoomService) {
        this.listRoomService = listRoomService;
        this.roomImageRepo = roomImageRepo;
        this.rentRoomService = rentRoomService;
    }

    @PostMapping(value = "/saveRooms",consumes = {"multipart/form-data"})
    public ResponseEntity<String> SaveRoomWithInfo(@RequestPart("roomData") String roomData,
                                                   @RequestPart(value="image",required = false) MultipartFile[] image){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            ListRooms listRooms = mapper.readValue(roomData, ListRooms.class);
            if(image != null && image.length > 0){
                System.out.println("Total Image received :" + image.length );

                for(MultipartFile images : image){
                    RoomImage roomImage = new RoomImage(
                            images.getOriginalFilename(),
                            images.getContentType(),
                            images.getBytes()
                    );
                    roomImage.setListRooms(listRooms);
                    listRooms.getRoomImages().add(roomImage);

                    System.out.println("Image received: " + images.getOriginalFilename());


                }
            }

            listRoomService.saveListedRoom(listRooms);
            System.out.println("Title: " + listRooms.getTitle());
            System.out.println("Rent: " + listRooms.getRent());
            System.out.println("SecurityDeposit: " + listRooms.getSecurityDeposit());
            System.out.println("PhoneNumber: " + listRooms.getPhoneNumber());
            System.out.println("AlternateNumber: " + listRooms.getAlternateNumber());

            return ResponseEntity.status(HttpStatus.CREATED).body("Room saved SuccessFully");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Error:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error:" + e.getMessage());
        }

    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomCardDto>>getAllRooms(){
        return ResponseEntity.ok(rentRoomService.getDataForRoomCard());
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]>getImage(@PathVariable Long id) {
        RoomImage img = roomImageRepo.findById(id).orElse(null);

        if (img == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(img.getContentType()))
                .body(img.getRoomImage());

    }


    @PostMapping("/filter")
    public List<ListRooms> filterRooms(@RequestBody RoomFilterDto roomFilterDto) {
        return rentRoomService.filter(roomFilterDto);
    }


}

