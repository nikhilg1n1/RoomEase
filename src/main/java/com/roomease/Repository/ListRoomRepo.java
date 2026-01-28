package com.roomease.Repository;

import com.roomease.DTO.RoomCardDto;
import com.roomease.Entity.ListRooms;
import com.roomease.Entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListRoomRepo extends JpaRepository<ListRooms,Long>, JpaSpecificationExecutor<ListRooms> {
    @Query("""
        select r from ListRooms r
        where lower(r.city) like %:query%
        or lower(r.address) like %:query%
""")
    List<ListRooms> searchByCityOrAddress(@Param("query") String query);

    ListRooms findListRoomsByRoomId(Long id);

    List<ListRooms> findListRoomsByUser(OauthUser user);
}
