package com.roomeaseauth.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomeaseauth.Entity.Amenities}
 */
@Value
public class AmenitiesDto implements Serializable {
    Long id;
    String wifi;
    String cctv;
    String parking;
    String powerBackup;
    String geyser;
    String security;
    String fridge;
    String washingMachine;
    String drinkingWater;
    String ac;
    String allTime;
    String houseKeeping;
    ListRoomsDto listRooms;

}