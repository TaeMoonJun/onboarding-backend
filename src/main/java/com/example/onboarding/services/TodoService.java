package com.example.onboarding.services;

import com.example.onboarding.DTOs.TodoDTO;
import com.example.onboarding.entities.Todo;
import com.example.onboarding.entities.User;
import com.example.onboarding.repositories.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoDTO createTodo(User user, TodoDTO todoDTO) {
        Todo todo = todoDTO.toEntity();
        user.addTodo(todo);
        todoRepository.save(todo);

        return todoDTO;
    }

    public List<TodoDTO> getTodos(User user) {
        return todoRepository.findAllByUserId(user.getId())
                .stream().map(todo -> new TodoDTO(todo.getContent()))
                .toList();
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
