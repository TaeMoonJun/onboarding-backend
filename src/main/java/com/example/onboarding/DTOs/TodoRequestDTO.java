package com.example.onboarding.DTOs;

import com.example.onboarding.entities.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequestDTO {
    private String todo;

    public Todo toEntity() {
        return Todo.builder()
                .content(todo)
                .build();
    }
}
