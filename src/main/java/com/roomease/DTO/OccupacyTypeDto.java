package com.roomease.DTO;

import com.roomease.Entity.OccupacyType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link OccupacyType}
 */
@Value
public class OccupacyTypeDto implements Serializable {
    Long id;
    String boys;
    String occupacy;
    String girls;
    String family;
}