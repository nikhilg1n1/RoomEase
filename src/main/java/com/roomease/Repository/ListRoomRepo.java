package com.roomease.Repository;

import com.roomease.Entity.ListRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ListRoomRepo extends JpaRepository<ListRooms,Long>, JpaSpecificationExecutor<ListRooms> {

}
