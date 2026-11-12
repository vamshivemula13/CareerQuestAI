package com.careerquestai.backend.service.impl;

import com.careerquestai.backend.dto.AuthResponseDTO;
import com.careerquestai.backend.dto.LoginRequestDTO;
import com.careerquestai.backend.dto.LoginResponseDTO;
import com.careerquestai.backend.dto.RegisterRequestDTO;
import com.careerquestai.backend.entity.User;
import com.careerquestai.backend.enums.Role;
import com.careerquestai.backend.repository.UserRepository;
import com.careerquestai.backend.security.JwtService;
import com.careerquestai.backend.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Role role = request.getRole() != null
                ? request.getRole()
                : Role.JOB_SEEKER;

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())

                // Store company during registration
                .company(request.getCompany())

                .role(role)
                .active(true)
                .build();

        userRepository.save(user);

        return AuthResponseDTO.builder()
                .message("Registration successful")
                .email(user.getEmail())
                .build();
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        if (user.getActive() == null || !user.getActive()) {
            throw new RuntimeException("User account is inactive");
        }

        String token = jwtService.generateToken(user.getEmail());

        return LoginResponseDTO.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .message("Login successful")
                .build();
    }
}