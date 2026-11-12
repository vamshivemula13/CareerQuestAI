package com.careerquestai.backend.service;

import com.careerquestai.backend.dto.ResumeResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ResumeService {

    ResumeResponseDTO uploadResume(
            MultipartFile file,
            String userEmail
    );

    ResumeResponseDTO getMyResume(
            String userEmail
    );

    void deleteResume(
            UUID resumeId
    );

    Resource getResumeForEmployer(
            UUID applicationId,
            String employerEmail
    );

    String getResumeContentType(
            UUID applicationId,
            String employerEmail
    );

    String getResumeFileName(
            UUID applicationId,
            String employerEmail
    );
}