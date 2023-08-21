package com.example.onboarding.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Todo> todos;

    @Enumerated
    private Authority authority;

    public void addTodo(Todo todo) {
        this.todos.add(todo);
        todo.setUser(this);
    }

}
