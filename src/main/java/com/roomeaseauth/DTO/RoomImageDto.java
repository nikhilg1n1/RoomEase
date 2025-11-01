package com.roomeaseauth.DTO;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomeaseauth.Entity.RoomImage}
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