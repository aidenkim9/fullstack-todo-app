package com.aiden.aiden_todo_app.todo.controller;

import com.aiden.aiden_todo_app.todo.dto.request.TodoCreateRequest;
import com.aiden.aiden_todo_app.todo.dto.response.TodoResponse;
import com.aiden.aiden_todo_app.todo.dto.request.TodoUpdateRequest;
import com.aiden.aiden_todo_app.todo.security.CustomUserDetails;
import com.aiden.aiden_todo_app.todo.service.TodoService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoResponse> allTodos(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        return todoService.findAll(customUserDetails.getUser());
    }

    @GetMapping("/{id}")
    public TodoResponse retreiveTodo(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws AccessDeniedException {
        return todoService.retreiveTodo(id, customUserDetails.getUser());
    }

    @PostMapping
    public TodoResponse createTodo(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody TodoCreateRequest request){
        return todoService.create(customUserDetails.getUser(), request);
    }

    @PatchMapping("/{id}")
    public void toggle(@PathVariable Integer id){
        todoService.toggle(id);
    }

    @PutMapping("/{id}")
    public void updateTodo(@PathVariable Integer id, @RequestBody TodoUpdateRequest request){
        todoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Integer id, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws AccessDeniedException {
        todoService.delete(id, customUserDetails.getUser());

    }
}
