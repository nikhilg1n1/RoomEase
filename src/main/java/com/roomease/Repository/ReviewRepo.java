package com.roomease.Repository;

import com.roomease.Entity.ListRooms;
import com.roomease.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review,Long> {
    List<Review> findByListRooms(ListRooms rooms);

    @Query("""
    SELECT r FROM Review r
    JOIN FETCH r.oauthUser
    JOIN FETCH r.listRooms
    WHERE r.listRooms.roomId = :roomId
""")
    List<Review> findByRoomId(@Param("roomId") Long roomId);
}

