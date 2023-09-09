package com.example.onboarding.services;

import com.example.onboarding.DTOs.TodoRequestDTO;
import com.example.onboarding.DTOs.TodoResponseDTO;
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

    public TodoResponseDTO createTodo(Long userId, TodoRequestDTO todoDTO) {
        User user = userRepository.findById(userId).get();
        Todo todo = Todo.builder()
                .user(user)
                .content(todoDTO.getTodo())
                .build();
        todoRepository.save(todo);

        return TodoResponseDTO.builder()
                .id(todo.getId())
                .todo(todo.getContent())
                .build();
    }

    public List<TodoResponseDTO> getTodos(Long userId) {
        return todoRepository.findAllByUserId(userId)
                .stream().map(todo -> new TodoResponseDTO(todo.getId(), todo.getContent()))
                .toList();
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO todoRequestDTO) {
        //isPresent()
        Todo todo = todoRepository.findById(id).get();
        todo.updateTodo(todoRequestDTO.getTodo());
        return TodoResponseDTO.builder()
                .id(todo.getId())
                .todo(todo.getContent())
                .build();
    }
}
