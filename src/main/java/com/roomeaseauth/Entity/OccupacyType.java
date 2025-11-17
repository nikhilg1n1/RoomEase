package com.roomeaseauth.Entity;

import jakarta.persistence.*;
import net.minidev.json.annotate.JsonIgnore;

@Entity
public class OccupacyType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String occupacy;

    @OneToOne
    @JoinColumn(name = "list_room_room_id")
    @JsonIgnore
    private ListRooms listRooms;

    public ListRooms getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRooms listRooms) {
        this.listRooms = listRooms;
    }

    public OccupacyType(String occupacy) {
        this.occupacy = occupacy;
    }

    public OccupacyType() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getOccupacy() {
        return occupacy;
    }

    public void setOccupacy(String occupacy) {
        this.occupacy = occupacy;
    }


}
