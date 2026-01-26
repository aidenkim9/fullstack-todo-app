package com.aiden.aiden_todo_app.todo.dto.request;

public class TodoUpdateRequest {

    private String title;
    private boolean completed;

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
}
