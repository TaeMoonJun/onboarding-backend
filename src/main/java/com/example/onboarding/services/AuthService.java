package com.example.onboarding.services;

import com.example.onboarding.DTOs.AuthRequest;
import com.example.onboarding.DTOs.SignUpResponse;
import com.example.onboarding.DTOs.Token;
import com.example.onboarding.entities.User;
import com.example.onboarding.jwt.JwtTokenProvider;
import com.example.onboarding.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(AuthRequest authRequest) {
        User user = userRepository.save(authRequest.toEntity(passwordEncoder));
        return SignUpResponse.builder()
                .email(user.getEmail())
                .build();
    }

    public Token signIn(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return Token.builder()
                .access_token(jwtTokenProvider.generateTokenDTO(authentication))
                .build();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
