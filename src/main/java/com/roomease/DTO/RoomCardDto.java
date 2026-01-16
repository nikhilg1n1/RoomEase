package com.roomease.DTO;

import com.roomease.Entity.ListRooms;

import java.io.Serializable;

public class RoomCardDto implements Serializable {
    private Long roomId;

    private String title;

    private Double rent;

    private String city;

    private Long imageId;



    public RoomCardDto(Long roomId, String title, Double rent, String city, Long imageId) {
        this.roomId = roomId;
        this.title = title;
        this.rent = rent;
        this.city = city;
        this.imageId = imageId;
    }

    public RoomCardDto() {
    }

    public RoomCardDto(Long imageId) {
        this.imageId = imageId;
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

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

}
