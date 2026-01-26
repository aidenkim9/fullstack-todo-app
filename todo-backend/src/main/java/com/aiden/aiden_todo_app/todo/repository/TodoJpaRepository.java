package com.aiden.aiden_todo_app.todo.repository;
//why using interface?
//why extends? not implements?
//what is this repository's construct? learn more details about repository

import com.aiden.aiden_todo_app.todo.entity.Todo;
import com.aiden.aiden_todo_app.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoJpaRepository extends JpaRepository<Todo, Integer> {

    // How work is this code?
    List<Todo> findByUser(User user);
}
