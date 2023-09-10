package com.example.onboarding.controllers;

import com.example.onboarding.DTOs.TodoRequest;
import com.example.onboarding.DTOs.TodoResponse;
import com.example.onboarding.services.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "JWT Token")
@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @Operation(summary = "Todo 생성")
    @PostMapping("")
    ResponseEntity<TodoResponse> createTodo(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoService.createTodo(Long.valueOf(userDetails.getUsername()), todoRequest));
    }

    @Operation(summary = "사용자 Todo 리스트 조회")
    @GetMapping("")
    ResponseEntity<List<TodoResponse>> getTodos(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(todoService.getTodos(Long.valueOf(userDetails.getUsername())));
    }
    
    @Operation(summary = "Todo 삭제")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Successfully Deleted");
    }

    @Operation(summary = "Todo 업데이트")
    @PutMapping("/{id}")
    ResponseEntity<TodoResponse> updateTodo(@PathVariable("id") Long id, @RequestBody TodoRequest todoRequest) {
        return ResponseEntity.ok(todoService.updateTodo(id, todoRequest));
    }
}
