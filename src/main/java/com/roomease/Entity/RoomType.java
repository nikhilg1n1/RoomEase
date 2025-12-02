package com.roomease.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class RoomType {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String typeName;

    private String single;

    private String shared;

    private String oneBHK;


    @OneToOne
    @JoinColumn(name = "list_rooms_room_id")
    @JsonIdentityReference(alwaysAsId = true)
    private ListRooms listRooms;

    public ListRooms getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRooms listRooms) {
        this.listRooms = listRooms;
    }


//    public RoomType(Long id, String typeName,String single, String shared, String oneBHK) {
//        this.id = id;
//        this.single = single;
//        this.shared = shared;
//        this.oneBHK = oneBHK;
//        this.typeName = typeName;
//    }

    public RoomType() {
    }

    public RoomType(String typeName) {
        this.typeName = typeName;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getOneBHK() {
        return oneBHK;
    }

    public void setOneBHK(String oneBHK) {
        this.oneBHK = oneBHK;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
