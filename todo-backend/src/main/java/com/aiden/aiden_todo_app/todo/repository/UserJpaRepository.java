package com.aiden.aiden_todo_app.todo.repository;

import com.aiden.aiden_todo_app.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
