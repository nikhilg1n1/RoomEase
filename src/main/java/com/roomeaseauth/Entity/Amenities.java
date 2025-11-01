package com.roomeaseauth.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity

public class Amenities {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String wifi;

    private String cctv;

    private String parking;

    private String powerBackup;

    private String geyser;

    private String security;

    private String fridge;

    private String washingMachine;

    private String drinkingWater;

    private String ac;

    private String allTime;

    private String houseKeeping;

    @OneToOne(mappedBy = "amenities")
    private ListRooms listRooms;

    public Amenities(Long id, String wifi, String cctv, String parking, String powerBackup, String geyser, String security, String fridge, String washingMachine, String drinkingWater, String ac, String allTime, String houseKeeping) {
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

    public Amenities() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }



    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getPowerBackup() {
        return powerBackup;
    }

    public void setPowerBackup(String powerBackup) {
        this.powerBackup = powerBackup;
    }

    public String getGeyser() {
        return geyser;
    }

    public void setGeyser(String geyser) {
        this.geyser = geyser;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getFridge() {
        return fridge;
    }

    public void setFridge(String fridge) {
        this.fridge = fridge;
    }

    public String getWashingMachine() {
        return washingMachine;
    }

    public void setWashingMachine(String washingMachine) {
        this.washingMachine = washingMachine;
    }

    public String getDrinkingWater() {
        return drinkingWater;
    }

    public void setDrinkingWater(String drinkingWater) {
        this.drinkingWater = drinkingWater;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public String getAllTime() {
        return allTime;
    }

    public void setAllTime(String allTime) {
        this.allTime = allTime;
    }

    public String getHouseKeeping() {
        return houseKeeping;
    }

    public void setHouseKeeping(String houseKeeping) {
        this.houseKeeping = houseKeeping;
    }

    public String getCctv() {
        return cctv;
    }

    public void setCctv(String cctv) {
        this.cctv = cctv;
    }

    public ListRooms getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRooms listRooms) {
        this.listRooms = listRooms;
    }
}
