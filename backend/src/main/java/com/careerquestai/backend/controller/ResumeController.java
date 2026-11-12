package com.careerquestai.backend.controller;

import com.careerquestai.backend.dto.ResumeResponseDTO;
import com.careerquestai.backend.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/api/resumes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ResumeController {

    private final ResumeService resumeService;


    // =========================================================
    // UPLOAD RESUME
    // =========================================================

    @PostMapping("/upload")
    public ResponseEntity<ResumeResponseDTO> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        return ResponseEntity.ok(
                resumeService.uploadResume(
                        file,
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // GET MY RESUME
    // =========================================================

    @GetMapping("/me")
    public ResponseEntity<ResumeResponseDTO> getMyResume(
            Authentication authentication) {

        return ResponseEntity.ok(
                resumeService.getMyResume(
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // DELETE MY RESUME
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(
            @PathVariable UUID id) {

        resumeService.deleteResume(id);

        return ResponseEntity.ok(
                "Resume deleted successfully"
        );
    }


    // =========================================================
    // EMPLOYER - VIEW CANDIDATE RESUME
    // =========================================================

    @GetMapping("/application/{applicationId}/view")
    public ResponseEntity<Resource> viewCandidateResume(
            @PathVariable UUID applicationId,
            Authentication authentication) {

        Resource resource =
                resumeService.getResumeForEmployer(
                        applicationId,
                        authentication.getName()
                );

        String contentType =
                resumeService.getResumeContentType(
                        applicationId,
                        authentication.getName()
                );

        String fileName =
                resumeService.getResumeFileName(
                        applicationId,
                        authentication.getName()
                );

        ContentDisposition contentDisposition =
                ContentDisposition.inline()
                        .filename(
                                fileName,
                                StandardCharsets.UTF_8
                        )
                        .build();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        contentDisposition.toString()
                )
                .body(resource);
    }


    // =========================================================
    // EMPLOYER - DOWNLOAD CANDIDATE RESUME
    // =========================================================

    @GetMapping("/application/{applicationId}/download")
    public ResponseEntity<Resource> downloadCandidateResume(
            @PathVariable UUID applicationId,
            Authentication authentication) {

        Resource resource =
                resumeService.getResumeForEmployer(
                        applicationId,
                        authentication.getName()
                );

        String contentType =
                resumeService.getResumeContentType(
                        applicationId,
                        authentication.getName()
                );

        String fileName =
                resumeService.getResumeFileName(
                        applicationId,
                        authentication.getName()
                );

        ContentDisposition contentDisposition =
                ContentDisposition.attachment()
                        .filename(
                                fileName,
                                StandardCharsets.UTF_8
                        )
                        .build();

        return ResponseEntity.ok()
                .contentType(
                        MediaType.parseMediaType(contentType)
                )
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        contentDisposition.toString()
                )
                .body(resource);
    }
}