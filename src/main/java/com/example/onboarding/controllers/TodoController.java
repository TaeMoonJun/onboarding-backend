package com.example.onboarding.controllers;

import com.example.onboarding.DTOs.TodoDTO;
import com.example.onboarding.entities.User;
import com.example.onboarding.services.AuthService;
import com.example.onboarding.services.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    private final AuthService authService;

    @PostMapping("")
    ResponseEntity<TodoDTO> createTodo(Principal principal, @RequestBody TodoDTO todoDTO) {
        Optional<User> optionalUser = authService.getUserById(Long.valueOf(principal.getName()));
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else throw new UsernameNotFoundException("Not found");

        return ResponseEntity.ok(todoService.createTodo(user, todoDTO));
    }

}
