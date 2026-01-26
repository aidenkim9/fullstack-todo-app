package com.aiden.aiden_todo_app.todo.security;

import com.aiden.aiden_todo_app.todo.entity.User;
import com.aiden.aiden_todo_app.todo.repository.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public CustomUserDetailsService(UserJpaRepository userJpaRepository){
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );
        return new CustomUserDetails(user);
    }
}
