package com.example.onboarding.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;

}