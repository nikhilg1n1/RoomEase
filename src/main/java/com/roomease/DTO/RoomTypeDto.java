package com.roomease.DTO;

import com.roomease.Entity.ListRooms;
import com.roomease.Entity.RoomType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RoomType}
 */
public class RoomTypeDto implements Serializable {
    Long id;
    String typeName;
    String single;
    String shared;
    String oneBHK;


    public RoomTypeDto() {
    }

    public Long getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getSingle() {
        return single;
    }

    public String getShared() {
        return shared;
    }

    public String getOneBHK() {
        return oneBHK;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public void setOneBHK(String oneBHK) {
        this.oneBHK = oneBHK;
    }

}