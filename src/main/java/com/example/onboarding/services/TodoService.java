package com.example.onboarding.services;

import com.example.onboarding.DTOs.TodoRequest;
import com.example.onboarding.DTOs.TodoResponse;
import com.example.onboarding.entities.Todo;
import com.example.onboarding.entities.User;
import com.example.onboarding.repositories.TodoRepository;
import com.example.onboarding.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoResponse createTodo(Long userId, TodoRequest todoRequest) {
        User user = userRepository.findById(userId).get();
        Todo todo = Todo.builder()
                .user(user)
                .content(todoRequest.getTodo())
                .build();
        todoRepository.save(todo);

        return TodoResponse.builder()
                .id(todo.getId())
                .todo(todo.getContent())
                .build();
    }

    public List<TodoResponse> getTodos(Long userId) {
        return todoRepository.findAllByUserId(userId)
                .stream().map(todo -> new TodoResponse(todo.getId(), todo.getContent()))
                .toList();
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoResponse updateTodo(Long id, TodoRequest todoRequest) {
        //isPresent()
        Todo todo = todoRepository.findById(id).get();
        todo.updateTodo(todoRequest.getTodo());
        return TodoResponse.builder()
                .id(todo.getId())
                .todo(todo.getContent())
                .build();
    }
}
