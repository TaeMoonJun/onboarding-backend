package com.example.onboarding.services;

import com.example.onboarding.DTOs.AuthRequestDTO;
import com.example.onboarding.DTOs.SignUpResponseDTO;
import com.example.onboarding.DTOs.TokenDTO;
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

    public SignUpResponseDTO signUp(AuthRequestDTO authRequestDTO) {
        User user = userRepository.save(authRequestDTO.toEntity(passwordEncoder));
        return SignUpResponseDTO.builder()
                .email(user.getEmail())
                .build();
    }// retrun 타입 바꿔야함

    public TokenDTO signIn(AuthRequestDTO authRequestDTO) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return TokenDTO.builder()
                .access_token(jwtTokenProvider.generateTokenDTO(authentication))
                .build();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
