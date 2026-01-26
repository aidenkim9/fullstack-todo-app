package com.aiden.aiden_todo_app.todo.service;

import com.aiden.aiden_todo_app.todo.dto.request.UserCreateRequest;
import com.aiden.aiden_todo_app.todo.entity.User;
import com.aiden.aiden_todo_app.todo.repository.UserJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder){
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Integer createUser(UserCreateRequest request){

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(request.getUsername(), request.getEmail(), encodedPassword);
        return userJpaRepository.save(user).getId();

    }
}
