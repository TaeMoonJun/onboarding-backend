package com.example.onboarding.services;

import com.example.onboarding.DTOs.AuthDTO;
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

    public AuthDTO registerNewUserAccount(AuthDTO authDTO) {
        User user = userRepository.save(authDTO.toEntity(passwordEncoder));
        return AuthDTO.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }// retrun 타입 바꿔야함

    public TokenDTO signIn(AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateTokenDTO(authentication);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
