package com.roomeaseauth.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roomeaseauth.Entity.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.roomeaseauth.Entity.ListRooms}
 */
@Data
public class ListRoomsDto implements Serializable {
    Long roomId;
    String title;
    String description;
    int rent;
    int securityDeposit;
    int beds;
    boolean attachedWashroom;
    boolean balcony;
    String address;
    String city;
    String landmark;
    String phoneNumber;
    String alternateNumber;
    String email;
    LocalDate availableDate;
    List<RoomImage> roomImages;
    Furnishing furnishingType;
    OccupacyType occupacyType;
    RoomType roomType;
    Amenities amenities;

    public ListRoomsDto(Long roomId, String title, String description, int rent, int securityDeposit, int beds, boolean attachedWashroom, boolean balcony, String address, String city, String landmark, String phoneNumber, String alternateNumber, String email, LocalDate availableDate, List<RoomImage> roomImages, Furnishing furnishingType, OccupacyType occupacyType, RoomType roomType, Amenities amenities) {
        this.roomId = roomId;
        this.title = title;
        this.description = description;
        this.rent = rent;
        this.securityDeposit = securityDeposit;
        this.beds = beds;
        this.attachedWashroom = attachedWashroom;
        this.balcony = balcony;
        this.address = address;
        this.city = city;
        this.landmark = landmark;
        this.phoneNumber = phoneNumber;
        this.alternateNumber = alternateNumber;
        this.email = email;
        this.availableDate = availableDate;
        this.roomImages = roomImages;
        this.furnishingType = furnishingType;
        this.occupacyType = occupacyType;
        this.roomType = roomType;
        this.amenities = amenities;
    }


}