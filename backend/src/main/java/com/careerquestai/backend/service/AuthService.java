package com.careerquestai.backend.service;

import com.careerquestai.backend.dto.AuthResponseDTO;
import com.careerquestai.backend.dto.LoginRequestDTO;
import com.careerquestai.backend.dto.LoginResponseDTO;
import com.careerquestai.backend.dto.RegisterRequestDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);

}