package com.careerquestai.backend.controller;

import com.careerquestai.backend.dto.AuthResponseDTO;
import com.careerquestai.backend.dto.LoginRequestDTO;
import com.careerquestai.backend.dto.LoginResponseDTO;
import com.careerquestai.backend.dto.RegisterRequestDTO;
import com.careerquestai.backend.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {

        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }
}