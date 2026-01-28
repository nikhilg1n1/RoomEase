package com.roomease.DTO;

import java.time.LocalDateTime;

public class ReviewDto {

    private Long id;
    private int rating;
    private String comment;
    private String userName;
    private LocalDateTime createdAt;

    public ReviewDto(
            Long id,
            int rating,
            String comment,
            String userName,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // getters only
}
