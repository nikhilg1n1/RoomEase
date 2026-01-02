package com.roomease.DTO;

import com.roomease.Entity.Furnishing;
import com.roomease.Entity.ListRooms;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Furnishing}
 */
public class FurnishingDto implements Serializable {
    Long id;
    String furnished;
    String furnishedType;
    String semiFurnished;
    String unFurnished;




    public FurnishingDto(Long id, String furnished, String furnishedType, String semiFurnished, String unFurnished) {
        this.id = id;
        this.furnished = furnished;
        this.furnishedType = furnishedType;
        this.semiFurnished = semiFurnished;
        this.unFurnished = unFurnished;
    }

    public FurnishingDto() {
    }


    public Long getId() {
        return id;
    }

    public String getFurnished() {
        return furnished;
    }

    public String getFurnishedType() {
        return furnishedType;
    }

    public String getSemiFurnished() {
        return semiFurnished;
    }

    public String getUnFurnished() {
        return unFurnished;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFurnished(String furnished) {
        this.furnished = furnished;
    }

    public void setFurnishedType(String furnishedType) {
        this.furnishedType = furnishedType;
    }

    public void setSemiFurnished(String semiFurnished) {
        this.semiFurnished = semiFurnished;
    }

    public void setUnFurnished(String unFurnished) {
        this.unFurnished = unFurnished;
    }


}