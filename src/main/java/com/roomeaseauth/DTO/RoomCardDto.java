package com.roomeaseauth.DTO;

public class RoomCardDto {
    private Long roomId;

    private String title;

    private int rent;

    private String city;

    private Long imageId;


    public RoomCardDto(Long roomId, String title, int rent, String city, Long imageId) {
        this.roomId = roomId;
        this.title = title;
        this.rent = rent;
        this.city = city;
        this.imageId = imageId;
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

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
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
