package com.careerquestai.backend.service;

import com.careerquestai.backend.dto.ApplicationRequestDTO;
import com.careerquestai.backend.dto.ApplicationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    ApplicationResponseDTO applyForJob(
            ApplicationRequestDTO request,
            String userEmail
    );

    List<ApplicationResponseDTO> getMyApplications(
            String userEmail
    );

    List<ApplicationResponseDTO> getApplicationsByJob(
            UUID jobId,
            String employerEmail
    );
}