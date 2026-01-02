package com.roomease.DTO;

import com.roomease.Entity.RoomImage;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RoomImage}
 */
public class RoomImageDto implements Serializable {
    Long id;
    String filename;
    String contentType;
    byte[] roomImage;

    public RoomImageDto() {
    }


    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getRoomImage() {
        return roomImage;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setRoomImage(byte[] roomImage) {
        this.roomImage = roomImage;
    }


}