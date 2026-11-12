package com.careerquestai.backend.controller;

import com.careerquestai.backend.dto.UserProfileResponseDTO;
import com.careerquestai.backend.dto.UserProfileUpdateDTO;
import com.careerquestai.backend.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> getMyProfile(
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.getMyProfile(authentication.getName())
        );
    }

    @PutMapping("/me")
    public ResponseEntity<UserProfileResponseDTO> updateMyProfile(
            @Valid @RequestBody UserProfileUpdateDTO request,
            Authentication authentication) {

        return ResponseEntity.ok(
                userService.updateMyProfile(
                        authentication.getName(),
                        request
                )
        );
    }
    
    @DeleteMapping("/me")
public ResponseEntity<String> deleteMyAccount(
        Authentication authentication) {

    userService.deleteMyAccount(authentication.getName());

    return ResponseEntity.ok(
            "Account deleted successfully"
    );
}
}