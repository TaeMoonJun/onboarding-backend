package com.example.onboarding.controllers;

import com.example.onboarding.DTOs.TodoDTO;
import com.example.onboarding.entities.User;
import com.example.onboarding.services.AuthService;
import com.example.onboarding.services.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "JWT Token")
@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    private final AuthService authService;

    @Operation(summary = "Todo 생성")
    @PostMapping("")
    ResponseEntity<TodoDTO> createTodo(Principal principal, @RequestBody TodoDTO todoDTO) {
        Optional<User> optionalUser = authService.getUserById(Long.valueOf(principal.getName()));
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else throw new UsernameNotFoundException("Not found");

        return ResponseEntity.ok(todoService.createTodo(user, todoDTO));
    }

    @Operation(summary = "사용자 Todo 리스트 조회")
    @GetMapping("")
    ResponseEntity<List<TodoDTO>> getTodos(Principal principal) {
        Optional<User> optionalUser = authService.getUserById(Long.valueOf(principal.getName()));
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else throw new UsernameNotFoundException("User Not found");

        return ResponseEntity.ok(todoService.getTodos(user));
    }
    
    @Operation(summary = "Todo 삭제")
    @DeleteMapping("/{id}")
    ResponseEntity deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
