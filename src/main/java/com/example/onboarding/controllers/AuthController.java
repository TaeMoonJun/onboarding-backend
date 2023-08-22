package com.example.onboarding.controllers;

import com.example.onboarding.DTOs.AuthDTO;
import com.example.onboarding.DTOs.TokenDTO;
import com.example.onboarding.services.AuthService;
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

    @PostMapping("/signup")
    public ResponseEntity<AuthDTO> registerUserAccount(@RequestBody @Valid AuthDTO authDTO){
        return ResponseEntity.ok(authService.registerNewUserAccount(authDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signIn(@RequestBody @Valid AuthDTO authDTO){
        return ResponseEntity.ok(authService.signIn(authDTO));
    }

}
