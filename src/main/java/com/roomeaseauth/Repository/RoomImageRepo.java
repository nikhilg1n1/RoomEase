package com.roomeaseauth.Repository;

import com.roomeaseauth.Entity.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomImageRepo extends JpaRepository<RoomImage,Long> {

}
