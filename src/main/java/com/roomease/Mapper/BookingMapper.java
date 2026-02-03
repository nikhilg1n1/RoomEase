package com.roomease.Mapper;

import com.roomease.DTO.BookingDto;
import com.roomease.DTO.OauthUserDto;
import com.roomease.Entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring")
public interface BookingMapper {
    @Mapping(source = "oauthUser.name",target = "name")
    @Mapping(target = "listRooms", ignore = true) // 🔥 BREAK LOOP

    BookingDto toDto(Booking booking);

    List<BookingDto> toDtoList(List<Booking> bookings);
}
