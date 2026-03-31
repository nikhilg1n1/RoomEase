package com.roomease.DTO;

import com.roomease.Entity.BookingStatus;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.roomease.Entity.Booking}
 */
public class BookingDto implements Serializable {
    Long id;
//    OauthUserDto oauthUser;
    ListRoomsDto listRooms;

    String name;
    Long roomId;
    Double rent;
    BookingStatus status;
    LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public OauthUserDto getOauthUser() {
//        return oauthUser;
//    }
//
//    public void setOauthUser(OauthUserDto oauthUser) {
//        this.oauthUser = oauthUser;
//    }

    public ListRoomsDto getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRoomsDto listRooms) {
        this.listRooms = listRooms;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}