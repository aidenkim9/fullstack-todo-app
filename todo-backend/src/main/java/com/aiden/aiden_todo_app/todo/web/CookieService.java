package com.aiden.aiden_todo_app.todo.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieService {

    public String getRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()){
            if("refreshToken".equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    public void setRefreshTokenCookie(HttpServletResponse response, String token) {

        Cookie cookie = new Cookie("refreshToken", token);

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);



    }
}
