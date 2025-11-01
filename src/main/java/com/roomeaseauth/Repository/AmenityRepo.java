package com.roomeaseauth.Repository;

import com.roomeaseauth.Entity.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepo extends JpaRepository<Amenities,Long> {
}
