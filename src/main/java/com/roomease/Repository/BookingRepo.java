package com.roomease.Repository;

import com.roomease.Entity.Booking;
import com.roomease.Entity.BookingStatus;
import com.roomease.Entity.ListRooms;
import com.roomease.Entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepo extends JpaRepository<Booking,Long> {

    boolean existsByOauthUserAndListRoomsAndStatus(OauthUser oauthUser,ListRooms listRooms, BookingStatus status);
}
