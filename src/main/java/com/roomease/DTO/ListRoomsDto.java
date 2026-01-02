package com.roomease.DTO;

import com.roomease.Entity.Amenities;
import com.roomease.Entity.Furnishing;
import com.roomease.Entity.OccupacyType;
import com.roomease.Entity.RoomImage;
import com.roomease.Entity.RoomType;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.roomease.Entity.ListRooms}
 */
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
    List<RoomImageDto> roomImages;
    FurnishingDto furnishingType;
    OccupacyTypeDto occupacyType;
    RoomTypeDto roomType;
    AmenitiesDto amenities;

    public ListRoomsDto(Long roomId, String title, String description, int rent, int securityDeposit, int beds, boolean attachedWashroom, boolean balcony, String address, String city, String landmark, String phoneNumber, String alternateNumber, String email, LocalDate availableDate, List<RoomImageDto> roomImages, FurnishingDto furnishingType, OccupacyTypeDto occupacyType, RoomTypeDto roomType, AmenitiesDto amenities) {
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

    public ListRoomsDto() {
    }


    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public int getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(int securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public boolean isAttachedWashroom() {
        return attachedWashroom;
    }

    public void setAttachedWashroom(boolean attachedWashroom) {
        this.attachedWashroom = attachedWashroom;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternateNumber() {
        return alternateNumber;
    }

    public void setAlternateNumber(String alternateNumber) {
        this.alternateNumber = alternateNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public List<RoomImageDto> getRoomImages() {
        return roomImages;
    }

    public void setRoomImages(List<RoomImageDto> roomImages) {
        this.roomImages = roomImages;
    }

    public FurnishingDto getFurnishingType() {
        return furnishingType;
    }

    public void setFurnishingType(FurnishingDto furnishingType) {
        this.furnishingType = furnishingType;
    }

    public OccupacyTypeDto getOccupacyType() {
        return occupacyType;
    }

    public void setOccupacyType(OccupacyTypeDto occupacyType) {
        this.occupacyType = occupacyType;
    }

    public RoomTypeDto getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeDto roomType) {
        this.roomType = roomType;
    }

    public AmenitiesDto getAmenities() {
        return amenities;
    }

    public void setAmenities(AmenitiesDto amenities) {
        this.amenities = amenities;
    }
}