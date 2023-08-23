package com.example.onboarding.DTOs;

import com.example.onboarding.entities.Authority;
import com.example.onboarding.entities.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@Data
public class AuthRequestDTO {
    private String email;
    private String password;

    public User toEntity(PasswordEncoder passwordEncoder){
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .build();
    }
}
