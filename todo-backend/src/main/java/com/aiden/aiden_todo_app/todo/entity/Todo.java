package com.aiden.aiden_todo_app.todo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private boolean completed = false;

    // target date

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Todo(){};

    public Todo(String title, User user){

        this.title = title;
        this.user = user;

    }

    public User getUser() {
        return user;
    }

    public void toggle(){
        this.completed = !this.completed;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void update(String title, boolean completed){
        this.title = title;
        this.completed = completed;
    }
}
