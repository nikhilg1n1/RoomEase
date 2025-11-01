package com.roomeaseauth.Repository;

import com.roomeaseauth.Entity.Furnishing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FurnishingTypeRepo extends JpaRepository<Furnishing,Long> {
}
