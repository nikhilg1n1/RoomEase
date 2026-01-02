package com.roomease.DTO;

import com.roomease.DTO.*;
import com.roomease.Entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {
//
//    @Mapping(target = "roomImages", source = "roomImages", ignore = true)
//    @Mapping(target = "amenities", source = "amenities",ignore = true)
//    @Mapping(target = "furnishingType", source = "furnishingType",ignore = true)
//    @Mapping(target = "occupacyType", source = "occupacyType",ignore = true)
//    @Mapping(target = "roomType", source = "roomType",ignore = true)
    ListRoomsDto toDto(ListRooms room);

//    @Mapping(target = "listRooms", ignore = true)// prevent infinite loop
//@Mapping(target = "roomImage", source = "roomImage")
RoomImageDto toDto(RoomImage roomImage);

//    @Mapping(target = "listRooms", ignore = true)
//@Mapping(target = "listRooms", source = "listRooms")
AmenitiesDto toDto(Amenities amenities);

//    @Mapping(target = "listRooms", ignore = true)
    RoomTypeDto toDto(RoomType roomType);

//    @Mapping(target = "listRooms", ignore = true)
    OccupacyTypeDto toDto(OccupacyType occupacyType);

//    @Mapping(target = "listRooms", ignore = true)
    FurnishingDto toDto(Furnishing furnishing);
}
