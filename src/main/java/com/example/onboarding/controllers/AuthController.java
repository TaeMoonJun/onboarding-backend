package com.example.onboarding.controllers;

import com.example.onboarding.DTOs.AuthRequestDTO;
import com.example.onboarding.DTOs.SignUpResponseDTO;
import com.example.onboarding.DTOs.TokenDTO;
import com.example.onboarding.config.NoAuth;
import com.example.onboarding.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @NoAuth
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody @Valid AuthRequestDTO authRequestDTO){
        return ResponseEntity.ok(authService.signUp(authRequestDTO));
    }

    @NoAuth
    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signIn(@RequestBody @Valid AuthRequestDTO authRequestDTO){
        return ResponseEntity.ok(authService.signIn(authRequestDTO));
    }

}
