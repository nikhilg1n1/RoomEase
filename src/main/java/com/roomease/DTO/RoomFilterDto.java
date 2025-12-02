package com.roomease.DTO;

import lombok.Data;

import java.util.List;

@Data
public class RoomFilterDto {

    private List<String> roomType;

    private  String minRent;

    private String maxRent;

    private List<String> occupacy;




    public RoomFilterDto( List<String> roomType, String minRent, String maxRent, List<String> occupacy) {
        this.roomType = roomType;
        this.minRent = minRent;
        this.maxRent = maxRent;
        this.occupacy = occupacy;
    }

    public RoomFilterDto() {
    }


    public List<String> getRoomType() {
        return roomType;
    }

    public void setRoomType(List<String> roomType) {
        this.roomType = roomType;
    }

    public List<String> getOccupacy() {
        return occupacy;
    }

    public void setOccupacy(List<String> occupacy) {
        this.occupacy = occupacy;
    }

    public String getMinRent() {
        return minRent;
    }

    public void setMinRent(String minRent) {
        this.minRent = minRent;
    }

    public String getMaxRent() {
        return maxRent;
    }

    public void setMaxRent(String maxRent) {
        this.maxRent = maxRent;
    }

}
