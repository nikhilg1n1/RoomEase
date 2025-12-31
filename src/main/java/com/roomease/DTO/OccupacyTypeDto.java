package com.roomease.DTO;

import com.roomease.Entity.ListRooms;
import com.roomease.Entity.OccupacyType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link OccupacyType}
 */

public class OccupacyTypeDto implements Serializable {
    Long id;
    String boys;
    String occupacy;

    String girls;
    String family;

    public OccupacyTypeDto() {
    }

    public OccupacyTypeDto(Long id, String boys, String occupacy, String girls, String family) {
        this.id = id;
        this.boys = boys;
        this.occupacy = occupacy;
        this.girls = girls;
        this.family = family;
    }

    public Long getId() {
        return id;
    }

    public String getBoys() {
        return boys;
    }

    public String getOccupacy() {
        return occupacy;
    }

    public String getGirls() {
        return girls;
    }

    public String getFamily() {
        return family;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBoys(String boys) {
        this.boys = boys;
    }

    public void setOccupacy(String occupacy) {
        this.occupacy = occupacy;
    }


    public void setGirls(String girls) {
        this.girls = girls;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}