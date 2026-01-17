package com.roomease.Entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id","room_id"})
        }
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oauth_user_id")
    private OauthUser oauthUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_rooms_room_id")
    private ListRooms listRooms;
    @Column(nullable = false)
    private int rating;
    @Column(length = 1000)
    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ListRooms getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRooms listRooms) {
        this.listRooms = listRooms;
    }

    public OauthUser getOauthUser() {
        return oauthUser;
    }

    public void setOauthUser(OauthUser oauthUser) {
        this.oauthUser = oauthUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @PrePersist
    void created(){
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void updated(){
        updatedAt = LocalDateTime.now();
    }
}
