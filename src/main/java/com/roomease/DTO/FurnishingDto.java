package com.roomease.DTO;

import com.roomease.Entity.Furnishing;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Furnishing}
 */
@Value
public class FurnishingDto implements Serializable {
    Long id;
    String furnished;
    String furnishedType;
    String semiFurnished;
    String unFurnished;
}