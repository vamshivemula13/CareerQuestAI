package com.careerquestai.backend.service.impl;

import com.careerquestai.backend.dto.ResumeResponseDTO;
import com.careerquestai.backend.entity.Application;
import com.careerquestai.backend.entity.Job;
import com.careerquestai.backend.entity.Resume;
import com.careerquestai.backend.entity.User;
import com.careerquestai.backend.repository.ApplicationRepository;
import com.careerquestai.backend.repository.ResumeRepository;
import com.careerquestai.backend.repository.UserRepository;
import com.careerquestai.backend.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final ApplicationRepository applicationRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;


    // =========================================================
    // UPLOAD RESUME
    // =========================================================

    @Override
    public ResumeResponseDTO uploadResume(
            MultipartFile file,
            String userEmail) {

        try {

            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() ->
                            new RuntimeException("User not found"));

            Files.createDirectories(Paths.get(uploadDir));

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null ||
                    originalFileName.trim().isEmpty()) {

                throw new RuntimeException(
                        "Invalid resume file"
                );
            }

            String storedFileName =
                    UUID.randomUUID() + "_" + originalFileName;

            Path filePath =
                    Paths.get(uploadDir, storedFileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            Resume resume = resumeRepository.findByUser(user)
                    .orElse(new Resume());

            resume.setUser(user);
            resume.setFileName(storedFileName);
            resume.setOriginalFileName(originalFileName);
            resume.setFileType(file.getContentType());
            resume.setFileSize(file.getSize());
            resume.setFilePath(filePath.toString());
            resume.setActive(true);

            resume = resumeRepository.save(resume);

            return ResumeResponseDTO.builder()
                    .id(resume.getId())
                    .fileName(resume.getFileName())
                    .originalFileName(resume.getOriginalFileName())
                    .fileType(resume.getFileType())
                    .fileSize(resume.getFileSize())
                    .uploadedAt(resume.getCreatedAt())
                    .message("Resume uploaded successfully")
                    .build();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload resume"
            );
        }
    }


    // =========================================================
    // GET MY RESUME
    // =========================================================

    @Override
    public ResumeResponseDTO getMyResume(
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Resume resume =
                resumeRepository.findByUserAndActiveTrue(user)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Resume not found"
                                ));

        return ResumeResponseDTO.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .originalFileName(resume.getOriginalFileName())
                .fileType(resume.getFileType())
                .fileSize(resume.getFileSize())
                .uploadedAt(resume.getCreatedAt())
                .message("Resume fetched successfully")
                .build();
    }


    // =========================================================
    // DELETE RESUME
    // =========================================================

    @Override
    public void deleteResume(UUID resumeId) {

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Resume not found"
                        ));

        resume.setActive(false);

        resumeRepository.save(resume);
    }


    // =========================================================
    // EMPLOYER - GET CANDIDATE RESUME
    // =========================================================

    @Override
    public Resource getResumeForEmployer(
            UUID applicationId,
            String employerEmail) {

        Resume resume =
                getAuthorizedApplicationResume(
                        applicationId,
                        employerEmail
                );

        Path path = Paths.get(resume.getFilePath());

        if (!Files.exists(path)) {

            throw new RuntimeException(
                    "Resume file not found on server"
            );
        }

        Resource resource =
                new FileSystemResource(path);

        if (!resource.exists() ||
                !resource.isReadable()) {

            throw new RuntimeException(
                    "Resume file cannot be read"
            );
        }

        return resource;
    }


    // =========================================================
    // EMPLOYER - RESUME CONTENT TYPE
    // =========================================================

    @Override
    public String getResumeContentType(
            UUID applicationId,
            String employerEmail) {

        Resume resume =
                getAuthorizedApplicationResume(
                        applicationId,
                        employerEmail
                );

        String contentType = resume.getFileType();

        if (contentType == null ||
                contentType.isBlank()) {

            try {

                Path path =
                        Paths.get(resume.getFilePath());

                contentType =
                        Files.probeContentType(path);

            } catch (IOException ignored) {

                contentType = null;
            }
        }

        if (contentType == null ||
                contentType.isBlank()) {

            contentType =
                    "application/octet-stream";
        }

        return contentType;
    }


    // =========================================================
    // EMPLOYER - RESUME FILE NAME
    // =========================================================

    @Override
    public String getResumeFileName(
            UUID applicationId,
            String employerEmail) {

        Resume resume =
                getAuthorizedApplicationResume(
                        applicationId,
                        employerEmail
                );

        String fileName =
                resume.getOriginalFileName();

        if (fileName == null ||
                fileName.isBlank()) {

            fileName = resume.getFileName();
        }

        return fileName;
    }


    // =========================================================
    // COMMON AUTHORIZATION CHECK
    // =========================================================

    private Resume getAuthorizedApplicationResume(
            UUID applicationId,
            String employerEmail) {

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Application not found"
                                ));

        User employer =
                userRepository.findByEmail(employerEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employer not found"
                                ));

        Job job = application.getJob();

        if (job == null) {

            throw new RuntimeException(
                    "Application job not found"
            );
        }

        if (job.getEmployer() == null) {

            throw new RuntimeException(
                    "Job employer not found"
            );
        }

        if (!job.getEmployer()
                .getId()
                .equals(employer.getId())) {

            throw new RuntimeException(
                    "You are not authorized to access this resume"
            );
        }

        Resume resume = application.getResume();

        if (resume == null) {

            throw new RuntimeException(
                    "No resume is attached to this application"
            );
        }

        return resume;
    }
}