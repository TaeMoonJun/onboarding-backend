package com.example.onboarding.DTOs;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SignUpResponseDTO {
    private String email;
}
