package com.roomeaseauth.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Furnishing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String furnished;
    private String furnishedType;
    private String semiFurnished;
    private String unFurnished;

    public Furnishing(String furnishedType) {
        this.furnishedType = furnishedType;
    }

    public Furnishing() {

    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setFurnished(String furnished) {
        this.furnished = furnished;
    }

    public void setSemiFurnished(String semiFurnished) {
        this.semiFurnished = semiFurnished;
    }

    public void setUnFurnished(String unFurnished) {
        this.unFurnished = unFurnished;
    }
}
