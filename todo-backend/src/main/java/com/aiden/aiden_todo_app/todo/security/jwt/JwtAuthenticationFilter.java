package com.aiden.aiden_todo_app.todo.security.jwt;


import com.aiden.aiden_todo_app.todo.entity.User;
import com.aiden.aiden_todo_app.todo.repository.UserJpaRepository;
import com.aiden.aiden_todo_app.todo.security.CustomUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserJpaRepository userJpaRepository;

    public JwtAuthenticationFilter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);

            try {
                Claims claims = JwtUtil.parseToken(token);

                Integer userId = claims.get("userId", Integer.class);
                String username = claims.getSubject();

                User user = userJpaRepository.findByUsername(username).orElseThrow();

                CustomUserDetails customUserDetails = new CustomUserDetails(user);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        customUserDetails, null, Collections.emptyList()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e){

                SecurityContextHolder.clearContext();

            }
        }

        filterChain.doFilter(request, response);

    }
}
