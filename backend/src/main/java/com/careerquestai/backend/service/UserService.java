package com.careerquestai.backend.service;

import com.careerquestai.backend.dto.UserProfileResponseDTO;
import com.careerquestai.backend.dto.UserProfileUpdateDTO;

public interface UserService {

    UserProfileResponseDTO getMyProfile(String userEmail);

    UserProfileResponseDTO updateMyProfile(
            String userEmail,
            UserProfileUpdateDTO request
    );
void deleteMyAccount(String userEmail);

}