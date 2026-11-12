package com.careerquestai.backend.service.impl;

import com.careerquestai.backend.dto.UserProfileResponseDTO;
import com.careerquestai.backend.dto.UserProfileUpdateDTO;
import com.careerquestai.backend.entity.User;
import com.careerquestai.backend.repository.UserRepository;
import com.careerquestai.backend.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserProfileResponseDTO getMyProfile(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    @Override
    public UserProfileResponseDTO updateMyProfile(
            String userEmail,
            UserProfileUpdateDTO request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setCompany(request.getCompany());

        User updatedUser = userRepository.save(user);

        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteMyAccount(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Soft delete
        user.setActive(false);

        userRepository.save(user);
    }

    private UserProfileResponseDTO mapToResponse(User user) {

        String role = "EMPLOYER";

        if (user.getRole() != null) {
            role = user.getRole().name();
        }

        return UserProfileResponseDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .company(user.getCompany())
                .role(role)
                .build();
    }
}