package com.roomeaseauth.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ListRooms {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;

    private String title;

    private String description;

    private int rent;

    private int securityDeposit;

    private int beds;

    private boolean attachedWashroom;

    private boolean balcony;

    private String address;

    private String city;

    private String landmark;

    private String phoneNumber;

    private String alternateNumber;

    private String email;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableDate;

    @OneToMany(mappedBy = "listRooms" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RoomImage> roomImages = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "furnishing_id")
    private Furnishing furnishingType;

    @ManyToOne
    private OccupacyType occupacyType;

    @OneToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(
            name = "room_amenities",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Amenities amenities;

    public ListRooms(Long roomId, String title, String description, int rent, int securityDeposit, int beds, boolean balcony, String address, String city, String landmark, String phoneNumber, String alternateNumber, String email, LocalDate availableDate, List<RoomImage> roomImages, Furnishing furnishingType, OccupacyType occupacyType, Amenities amenities) {
        this.roomId = roomId;
        this.title = title;
        this.description = description;
        this.rent = rent;
        this.securityDeposit = securityDeposit;
        this.beds = beds;
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
        this.amenities = amenities;
    }

    public ListRooms() {
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

    public List<RoomImage> getRoomImages() {
        return roomImages;
    }

    public void setRoomImages(List<RoomImage> roomImages) {
        this.roomImages = roomImages;
    }

    public Furnishing getFurnishingType() {
        return furnishingType;
    }

    public void setFurnishingType(Furnishing furnishingType) {
        this.furnishingType = furnishingType;
    }

    public OccupacyType getOccupacyType() {
        return occupacyType;
    }

    public void setOccupacyType(OccupacyType occupacyType) {
        this.occupacyType = occupacyType;
    }


    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }

    public boolean isAttachedWashroom() {
        return attachedWashroom;
    }

    public void setAttachedWashroom(boolean attachedWashroom) {
        this.attachedWashroom = attachedWashroom;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Amenities getAmenities() {
        return amenities;
    }
}
