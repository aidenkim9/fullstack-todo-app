package com.aiden.aiden_todo_app.todo.controller;

import com.aiden.aiden_todo_app.todo.dto.request.LoginRequest;
import com.aiden.aiden_todo_app.todo.dto.request.LogoutRequest;
import com.aiden.aiden_todo_app.todo.dto.response.LoginResponse;
import com.aiden.aiden_todo_app.todo.dto.request.RefreshRequest;
import com.aiden.aiden_todo_app.todo.entity.RefreshToken;
import com.aiden.aiden_todo_app.todo.entity.User;
import com.aiden.aiden_todo_app.todo.repository.RefreshTokenRepository;
import com.aiden.aiden_todo_app.todo.repository.UserJpaRepository;
import com.aiden.aiden_todo_app.todo.security.jwt.JwtUtil;
import com.aiden.aiden_todo_app.todo.service.AuthService;
import com.aiden.aiden_todo_app.todo.web.CookieService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserJpaRepository userJpaRepository;
    private final CookieService cookieService;


    public AuthController
            (AuthService authService,
             RefreshTokenRepository refreshTokenRepository,
             UserJpaRepository userJpaRepository,
             CookieService cookieService) {
        this.authService = authService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userJpaRepository = userJpaRepository;
        this.cookieService = cookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpServletResponse response) throws IllegalAccessException {

        LoginResponse loginResponse = authService.login(request);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", loginResponse.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-cookie", cookie.toString());

        return ResponseEntity.ok(new LoginResponse(loginResponse.getAccessToken(), null));

    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = cookieService.getRefreshToken(request);
        refreshTokenRepository.deleteByToken(refreshToken);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                        .path("/")
                                .maxAge(0)
                                        .build();

        response.addHeader("Set-cookie", deleteCookie.toString());

        authService.logout(refreshToken);
        return ResponseEntity.ok("logout");

    }

//    @PostMapping("/refresh")
//    public LoginResponse refresh(@RequestBody RefreshRequest request){
//
//        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken()).orElseThrow(
//                () -> new RuntimeException("Invalid refresh token")
//        );
//
//        Claims claims = JwtUtil.parseToken(refreshToken.getToken());
//        String username = claims.getSubject();
//
//        User user = userJpaRepository.findByUsername(username).orElseThrow();
//
//        String newAccess = JwtUtil.createAccessToken(user);
//
//        return new LoginResponse(newAccess, refreshToken.getToken());
//
//    }

    @PostMapping("/refresh")
    public LoginResponse refresh(HttpServletRequest request){

        String refreshToken = cookieService.getRefreshToken(request);

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElseThrow(
                () -> new RuntimeException("Invalid refresh token")
        );

        User user = userJpaRepository.findById(token.getUserId()).orElseThrow();

        String newAccess = JwtUtil.createAccessToken(user);

        return new LoginResponse(newAccess, refreshToken);

    }


}
