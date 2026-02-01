package com.aiden.aiden_todo_app.todo.service;

import com.aiden.aiden_todo_app.todo.dto.request.LoginRequest;
import com.aiden.aiden_todo_app.todo.dto.response.LoginResponse;
import com.aiden.aiden_todo_app.todo.entity.User;

import com.aiden.aiden_todo_app.todo.repository.UserJpaRepository;
import com.aiden.aiden_todo_app.todo.security.jwt.JwtUtil;
import com.aiden.aiden_todo_app.todo.web.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    private final UserJpaRepository userJpaRepository;

    private final PasswordEncoder passwordEncoder;
    private final CookieService cookieService;
    private final RedisTemplate<String, String> redisTemplate;

    public AuthService(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder, CookieService cookieService, RedisTemplate<String, String> redisTemplate) {
        this.userJpaRepository = userJpaRepository;

        this.passwordEncoder = passwordEncoder;
        this.cookieService = cookieService;
        this.redisTemplate = redisTemplate;
    }

    public LoginResponse login(LoginRequest request, HttpServletResponse response) throws IllegalAccessException {

        User user = userJpaRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalAccessException("Invalid Password");
        }



        String accessToken = JwtUtil.createAccessToken(user);
        String refreshToken = JwtUtil.createRefreshToken(user);

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-cookie", cookie.toString());

        redisTemplate.opsForValue().set(
                "refresh:token:" + refreshToken,
                user.getId().toString(),
                14,
                TimeUnit.DAYS
        );

        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = cookieService.getRefreshToken(request);

        ResponseCookie deleteCookie = ResponseCookie.from("refreshToken", "")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-cookie", deleteCookie.toString());

        redisTemplate.delete("refresh:token:" + refreshToken);

        return "logout";
    }

    @Transactional
    public LoginResponse refresh(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = cookieService.getRefreshToken(request);

        String userId = redisTemplate.opsForValue().get("refresh:token:"+refreshToken);

        if(userId == null) {
            throw new RuntimeException("Invalid refresh token");
        }

        User user = userJpaRepository.findById(Integer.valueOf(userId)).orElseThrow();

        redisTemplate.delete("refresh:token:"+refreshToken);

        String newAccess = JwtUtil.createAccessToken(user);
        String newRefresh = JwtUtil.createRefreshToken(user);

        redisTemplate.opsForValue().set(
                "refresh:token:" + newRefresh,
                userId,
                14,
                TimeUnit.DAYS
        );

        cookieService.setRefreshTokenCookie(response, newRefresh);

        return new LoginResponse(newAccess, null);

    }
}
