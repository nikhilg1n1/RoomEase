package com.roomease.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String name;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oauthuser_id")
     private OauthUser oauthUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_rooms_id")
    private ListRooms listRooms;

    private Double rent;

    private Double deposit;

    private Double bookingToken;


    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private BookingStatus status;

    public Booking(Long id, String name, OauthUser oauthUser, ListRooms listRooms, Double rent, BookingStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.name=name;
        this.oauthUser = oauthUser;
        this.listRooms = listRooms;
        this.rent = rent;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Booking() {
    }

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OauthUser getOauthUser() {
        return oauthUser;
    }

    public void setOauthUser(OauthUser oauthUser) {
        this.oauthUser = oauthUser;
    }

    public ListRooms getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRooms listRooms) {
        this.listRooms = listRooms;
    }

    public Double getRent() {
        return rent;
    }

    public void setRent(Double rent) {
        this.rent = rent;
    }



    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getBookingToken() {
        return bookingToken;
    }

    public void setBookingToken(Double bookingToken) {
        this.bookingToken = bookingToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

