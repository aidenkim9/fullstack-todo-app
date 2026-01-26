package com.aiden.aiden_todo_app.todo.security.jwt;

import com.aiden.aiden_todo_app.todo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;


//static을 사용하는 이유, 코드의 용도 설명

public class JwtUtil {

    private static final SecretKey key = Keys.hmacShaKeyFor("aiden-secret-key-for-jwt-must-be-long".getBytes());
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 5;

    public static String createAccessToken(User user){

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    public static String createRefreshToken(User user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims parseToken(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
}
