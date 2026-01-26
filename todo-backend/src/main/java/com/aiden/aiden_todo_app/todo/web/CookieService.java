package com.aiden.aiden_todo_app.todo.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
}
