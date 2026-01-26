package com.aiden.aiden_todo_app.todo.service;

import com.aiden.aiden_todo_app.todo.dto.request.LoginRequest;
import com.aiden.aiden_todo_app.todo.dto.response.LoginResponse;
import com.aiden.aiden_todo_app.todo.entity.RefreshToken;
import com.aiden.aiden_todo_app.todo.entity.User;
import com.aiden.aiden_todo_app.todo.repository.RefreshTokenRepository;
import com.aiden.aiden_todo_app.todo.repository.UserJpaRepository;
import com.aiden.aiden_todo_app.todo.security.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private  final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserJpaRepository userJpaRepository, RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) throws IllegalAccessException {

        User user = userJpaRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new IllegalAccessException("Invalid Password");
        }

        String accessToken = JwtUtil.createAccessToken(user);
        String refreshToken = JwtUtil.createRefreshToken(user);

        refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));

        return new LoginResponse(accessToken, refreshToken);
    }

    @Transactional
    public String logout(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);

        return "logout";
    }
}
