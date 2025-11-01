package com.roomeaseauth.Repository;

import com.roomeaseauth.Entity.OccupacyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupacyRepo extends JpaRepository<OccupacyType,Long> {

}
