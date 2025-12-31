package com.roomease.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Furnishing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String furnished;
    private String furnishedType;
    private String semiFurnished;
    private String unFurnished;
    @ManyToOne
    @JoinColumn(name = "list_rooms_room_id")
    private ListRooms listRooms;

    public void setListRooms(ListRooms listRooms) {
        this.listRooms = listRooms;
    }


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
