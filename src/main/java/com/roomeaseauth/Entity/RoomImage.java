package com.roomeaseauth.Entity;

import jakarta.persistence.*;

@Entity
public class RoomImage {
    public RoomImage( String filename, String contentType, byte[] roomImage) {
        this.filename = filename;
        this.contentType = contentType;
        this.roomImage = roomImage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String filename;

    private String contentType;


    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] roomImage;

    @ManyToOne
    private ListRooms listRooms;

    public RoomImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(byte[] roomImage) {
        this.roomImage = roomImage;
    }

    public ListRooms getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRooms listRooms) {
        this.listRooms = listRooms;
    }
}
