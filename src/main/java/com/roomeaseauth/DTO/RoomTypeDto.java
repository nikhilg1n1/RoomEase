package com.roomeaseauth.DTO;

import com.roomeaseauth.Entity.RoomType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RoomType}
 */
@Value
public class RoomTypeDto implements Serializable {
    Long id;
    String typeName;
    String single;
    String shared;
    String oneBHK;
}