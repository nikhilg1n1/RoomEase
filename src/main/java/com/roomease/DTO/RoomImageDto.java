package com.roomease.DTO;

import com.roomease.Entity.RoomImage;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RoomImage}
 */
@Value
@Data
public class RoomImageDto implements Serializable {
    Long id;
    String filename;
    String contentType;
    byte[] roomImage;
    ListRoomsDto listRooms;
}