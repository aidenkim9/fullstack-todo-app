package com.aiden.aiden_todo_app.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private long id;

    private Integer userId;

    private String token;

    private LocalDateTime expiryDate;

    protected RefreshToken(){};

    public RefreshToken(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}
