package com.careerquestai.backend.controller;

import com.careerquestai.backend.dto.ApplicationRequestDTO;
import com.careerquestai.backend.dto.ApplicationResponseDTO;
import com.careerquestai.backend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ApplicationController {

    private final ApplicationService applicationService;


    // =========================================================
    // APPLY FOR JOB
    // =========================================================

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> applyForJob(
            @Valid @RequestBody ApplicationRequestDTO request,
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.applyForJob(
                        request,
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // GET MY APPLICATIONS
    // =========================================================

    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications(
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getMyApplications(
                        authentication.getName()
                )
        );
    }


    // =========================================================
    // GET APPLICATIONS FOR EMPLOYER JOB
    // =========================================================

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByJob(
            @PathVariable UUID jobId,
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getApplicationsByJob(
                        jobId,
                        authentication.getName()
                )
        );
    }
}