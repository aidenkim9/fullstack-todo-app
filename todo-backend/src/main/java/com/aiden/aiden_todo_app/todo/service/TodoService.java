package com.aiden.aiden_todo_app.todo.service;

import com.aiden.aiden_todo_app.todo.dto.request.TodoCreateRequest;
import com.aiden.aiden_todo_app.todo.dto.response.TodoResponse;
import com.aiden.aiden_todo_app.todo.dto.request.TodoUpdateRequest;
import com.aiden.aiden_todo_app.todo.entity.Todo;
import com.aiden.aiden_todo_app.todo.entity.User;
import com.aiden.aiden_todo_app.todo.repository.TodoJpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
public class TodoService {

    // Why typing final
    private final TodoJpaRepository todoJpaRepository;


    public TodoService(TodoJpaRepository todoJpaRepository){
        this.todoJpaRepository = todoJpaRepository;

    }

    public TodoResponse create(User user, TodoCreateRequest request){

        Todo todo = todoJpaRepository.save(new Todo(request.getTitle(), user));
        return new TodoResponse(todo.getId(), todo.getTitle(), todo.isCompleted());

    }

    public List<TodoResponse> findAll(User user){

        return todoJpaRepository.findByUser(user)
                .stream().map(
                        todo -> new TodoResponse(
                                todo.getId(), todo.getTitle(), todo.isCompleted()
                        )
                )
                .toList();
    }

    public TodoResponse retreiveTodo(Integer id, User user) throws AccessDeniedException {
       Todo todo =  todoJpaRepository.findById(id).orElseThrow();
       if(!todo.getUser().getId().equals(user.getId())){
           throw new AccessDeniedException("No valid");
       }
       return new TodoResponse(
               todo.getId(), todo.getTitle(), todo.isCompleted()
       );
    }

    public void toggle(Integer id){

        Todo todo = todoJpaRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Todo not found")
        );
        todo.toggle();

    }

    public void update(Integer id, TodoUpdateRequest request){

        Todo todo = todoJpaRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("Todo not found")
        );
        todo.update(request.getTitle(), request.isCompleted());

    }

    public void delete(Integer todoId, User user) throws AccessDeniedException {

        Todo todo = todoJpaRepository.findById(todoId).orElseThrow();

        if(!user.getId().equals(todo.getUser().getId())){
            throw new AccessDeniedException("No Authentication Please Sign in");
        }

        todoJpaRepository.delete(todo);
    }

}
