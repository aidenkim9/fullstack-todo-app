package com.aiden.aiden_todo_app.todo.controller;

import com.aiden.aiden_todo_app.todo.dto.request.UserCreateRequest;
import com.aiden.aiden_todo_app.todo.dto.response.UserResponse;
import com.aiden.aiden_todo_app.todo.security.CustomUserDetails;
import com.aiden.aiden_todo_app.todo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<?> createAccount(@RequestBody UserCreateRequest userCreateRequest){

        Integer userId = userService.createUser(userCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping("/me")
    public UserResponse getMe(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return new UserResponse(
                customUserDetails.getUser().getId(), customUserDetails.getUser().getUsername()
        );
    }

}
