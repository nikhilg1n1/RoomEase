package com.roomease.DTO;

import com.roomease.DTO.ListRoomsDto;
import com.roomease.DTO.OauthUserDto;
import com.roomease.Entity.Review;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Review}
 */
@Data
public class ReviewDto implements Serializable {
    Long id;
    OauthUserDto oauthUser;
    ListRoomsDto listRooms;
    int rating;
    String comment;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OauthUserDto getOauthUser() {
        return oauthUser;
    }

    public void setOauthUser(OauthUserDto oauthUser) {
        this.oauthUser = oauthUser;
    }

    public ListRoomsDto getListRooms() {
        return listRooms;
    }

    public void setListRooms(ListRoomsDto listRooms) {
        this.listRooms = listRooms;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}