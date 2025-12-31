package com.roomease.DTO;

import com.roomease.Entity.Amenities;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Amenities}
 */
public class AmenitiesDto implements Serializable {
    Long id;
    String wifi;
    String cctv;
    String parking;
    String powerBackup;
    String geyser;
    String security;
    String fridge;
    String washingMachine;
    String drinkingWater;
    String ac;
    String allTime;
    String houseKeeping;


    public AmenitiesDto(Long id, String wifi, String cctv, String parking, String powerBackup, String geyser, String security, String fridge, String washingMachine, String drinkingWater, String ac, String allTime, String houseKeeping) {

        this.id = id;
        this.wifi = wifi;
        this.cctv = cctv;
        this.parking = parking;
        this.powerBackup = powerBackup;
        this.geyser = geyser;
        this.security = security;
        this.fridge = fridge;
        this.washingMachine = washingMachine;
        this.drinkingWater = drinkingWater;
        this.ac = ac;
        this.allTime = allTime;
        this.houseKeeping = houseKeeping;

    }

    public AmenitiesDto() {
    }

    public Long getId() {
        return id;
    }

    public String getWifi() {
        return wifi;
    }

    public String getCctv() {
        return cctv;
    }

    public String getParking() {
        return parking;
    }

    public String getPowerBackup() {
        return powerBackup;
    }

    public String getGeyser() {
        return geyser;
    }

    public String getSecurity() {
        return security;
    }

    public String getFridge() {
        return fridge;
    }

    public String getWashingMachine() {
        return washingMachine;
    }

    public String getDrinkingWater() {
        return drinkingWater;
    }

    public String getAc() {
        return ac;
    }

    public String getAllTime() {
        return allTime;
    }

    public String getHouseKeeping() {
        return houseKeeping;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public void setCctv(String cctv) {
        this.cctv = cctv;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public void setPowerBackup(String powerBackup) {
        this.powerBackup = powerBackup;
    }

    public void setGeyser(String geyser) {
        this.geyser = geyser;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public void setFridge(String fridge) {
        this.fridge = fridge;
    }

    public void setWashingMachine(String washingMachine) {
        this.washingMachine = washingMachine;
    }

    public void setDrinkingWater(String drinkingWater) {
        this.drinkingWater = drinkingWater;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    public void setHouseKeeping(String houseKeeping) {
        this.houseKeeping = houseKeeping;
    }

}