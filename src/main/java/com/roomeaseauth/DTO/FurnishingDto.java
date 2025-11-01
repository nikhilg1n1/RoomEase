package com.roomeaseauth.DTO;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.roomeaseauth.Entity.Furnishing}
 */
@Value
public class FurnishingDto implements Serializable {
    Long id;
    String furnished;
    String furnishedType;
    String semiFurnished;
    String unFurnished;
}