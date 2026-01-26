package com.aiden.aiden_todo_app.todo.dto.response;

public class UserResponse {

    private Integer id;
    private String username;

    public UserResponse(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
