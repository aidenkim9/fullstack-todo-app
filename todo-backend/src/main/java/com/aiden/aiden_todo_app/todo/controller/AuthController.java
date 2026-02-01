package com.aiden.aiden_todo_app.todo.controller;

import com.aiden.aiden_todo_app.todo.dto.request.LoginRequest;
import com.aiden.aiden_todo_app.todo.dto.response.LoginResponse;
import com.aiden.aiden_todo_app.todo.service.AuthService;
import com.aiden.aiden_todo_app.todo.web.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final CookieService cookieService;
    private final RedisTemplate<String, String> redisTemplate;


    public AuthController
            (AuthService authService,
             CookieService cookieService,
             RedisTemplate<String, String> redisTemplate) {
        this.authService = authService;
        this.cookieService = cookieService;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) throws IllegalAccessException {

        LoginResponse loginResponse = authService.login(request, response);

        return ResponseEntity.ok(new LoginResponse(loginResponse.getAccessToken(), loginResponse.getRefreshToken()));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        authService.logout(request, response);

        return ResponseEntity.ok("logout");

    }


    @PostMapping("/refresh")
    public LoginResponse refresh(HttpServletRequest request, HttpServletResponse response){

        return authService.refresh(request, response);

    }


}
