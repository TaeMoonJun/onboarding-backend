package com.example.onboarding.controllers;

import com.example.onboarding.DTOs.AuthRequest;
import com.example.onboarding.DTOs.SignUpResponse;
import com.example.onboarding.DTOs.Token;
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
    public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid AuthRequest authRequest){
        return ResponseEntity.ok(authService.signUp(authRequest));
    }

    @NoAuth
    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public ResponseEntity<Token> signIn(@RequestBody @Valid AuthRequest authRequest){
        return ResponseEntity.ok(authService.signIn(authRequest));
    }

}
