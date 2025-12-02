package com.roomease.DTO;

import com.roomease.Entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomImages", source = "roomImages")
    ListRoomsDto toDto(ListRooms room);

    RoomImageDto toDto(RoomImage roomImage);

    FurnishingDto toDto(Furnishing furnishing);

    OccupacyTypeDto toDto(OccupacyType occupacyType);

    RoomTypeDto toDto(RoomType roomType);

    AmenitiesDto toDto(Amenities amenities);

}
