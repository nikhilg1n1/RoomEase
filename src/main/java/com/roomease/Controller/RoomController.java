package com.roomease.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roomease.DTO.ListRoomsDto;
import com.roomease.DTO.ReviewDto;
import com.roomease.DTO.RoomCardDto;
import com.roomease.DTO.RoomFilterDto;
import com.roomease.Entity.*;
import com.roomease.Repository.*;
import com.roomease.Services.ListRoomService;
import com.roomease.Services.RentRoomService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
@RestController
@RequestMapping("/v1")
public class RoomController {
    private final ListRoomService listRoomService;

    private final RoomImageRepo roomImageRepo;

    private final OauthUserRepo oauthUserRepo;

    private final BookingRepo bookingRepo;

    private final RentRoomService rentRoomService;
    private final ReviewRepo reviewRepo;

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    private final ListRoomRepo listRoomRepo;

    public RoomController(ListRoomService listRoomService, RoomImageRepo roomImageRepo, OauthUserRepo oauthUserRepo, BookingRepo bookingRepo, RentRoomService rentRoomService, ReviewRepo reviewRepo,
                          ListRoomRepo listRoomRepo) {
        this.listRoomService = listRoomService;
        this.roomImageRepo = roomImageRepo;
        this.oauthUserRepo = oauthUserRepo;
        this.bookingRepo = bookingRepo;
        this.rentRoomService = rentRoomService;
        this.reviewRepo = reviewRepo;
        this.listRoomRepo = listRoomRepo;
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
//            listRooms.setUser();
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
    public ResponseEntity<byte[]>getImage(@PathVariable Long id, HttpServletRequest request)  {
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
    public List<RoomCardDto> filterRooms(@RequestBody RoomFilterDto roomFilterDto) {
        System.out.println("Room Ocuupacy in Controller :" + roomFilterDto.getOccupacy());
        System.out.println("Room Minimum Rent is:" + roomFilterDto.getMinRent());
        System.out.println("Room Maximum Rent is:" + roomFilterDto.getMaxRent());
        System.out.println("Room type is:" + roomFilterDto.getRoomType());
        return rentRoomService.filter(roomFilterDto);
    }

    @GetMapping("/description/{id}")
    public ResponseEntity<?> getRoomForDescription(@PathVariable Long id){

        System.out.println("Fetching the data for room Id :" + id);
        logger.info("Fetching the data for room Id -> {}", id);

        ListRoomsDto room = listRoomService.getRoomDescription(id);

        if(room == null){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    @GetMapping("/searchRooms")
    public List<RoomCardDto> searchRoomsByQuery(@RequestParam String query){
        return rentRoomService.searchRooms(query);
    }

    @PostMapping("/rooms/{roomId}/reviews")
    public ResponseEntity<?> addReview(@PathVariable Long roomId, @RequestBody ReviewDto dto, Authentication auth){
        String email = (String) auth.getPrincipal();
        OauthUser user =oauthUserRepo.findByEmail(email);
        ListRooms room = listRoomRepo.findListRoomsByRoomId(roomId);


//        boolean hasBooked = bookingRepo.existsByOauthUserAndListRoomsAndStatus(user, room ,BookingStatus.INITIATED);
//        if(!hasBooked){
//            return ResponseEntity.status(403).body("Only booked user can add rating and review to the rooms");
//        }
        Review review = new Review();
        review.setOauthUser(user);
        review.setListRooms(room);
        review.setComment(dto.getComment());
        review.setRating(dto.getRating());
        reviewRepo.save(review);

        rentRoomService.updateRoomReview(room);

        return ResponseEntity.ok("Review Added");
    }

    @GetMapping("/rooms/{roomId}/allreviews")
    public ResponseEntity<?>getRoomReview(@PathVariable Long roomId){

        listRoomRepo.findById(roomId).orElseThrow(()-> new RuntimeException("Room not found"));

        List<ReviewDto> reviews =
                reviewRepo.findByRoomId(roomId)
                        .stream()
                        .map( r ->  new ReviewDto(
                                r.getId(),
                                r.getRating(),
                                r.getComment(),
                                r.getOauthUser().getName(),
                                r.getCreatedAt()
                        )).toList();

        logger.info("Reviews size -> " + reviews.size());


        return ResponseEntity.ok(reviews);
    }
}

