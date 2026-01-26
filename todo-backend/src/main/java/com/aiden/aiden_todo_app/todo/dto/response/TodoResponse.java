package com.aiden.aiden_todo_app.todo.dto.response;

public class TodoResponse {
    private Integer id;
    private String title;
    private boolean completed;

    public TodoResponse(Integer id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
}
