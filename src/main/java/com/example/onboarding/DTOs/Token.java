package com.example.onboarding.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
    private String access_token;
}
