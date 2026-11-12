package com.careerquestai.backend.controller;

import com.careerquestai.backend.dto.JobRequestDTO;
import com.careerquestai.backend.dto.JobResponseDTO;
import com.careerquestai.backend.service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;


    // ==========================================
    // CREATE JOB
    // ==========================================

    @PostMapping
    public ResponseEntity<JobResponseDTO> createJob(
            @Valid @RequestBody JobRequestDTO request) {

        return ResponseEntity.ok(
                jobService.createJob(request)
        );
    }


    // ==========================================
    // GET EMPLOYER'S ACTIVE JOBS
    // ==========================================

    @GetMapping
    public ResponseEntity<List<JobResponseDTO>> getAllJobs() {

        return ResponseEntity.ok(
                jobService.getAllJobs()
        );
    }


    // ==========================================
    // GET JOB BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<JobResponseDTO> getJobById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                jobService.getJobById(id)
        );
    }


    // ==========================================
    // UPDATE JOB
    // ==========================================

    @PutMapping("/{id}")
    public ResponseEntity<JobResponseDTO> updateJob(
            @PathVariable UUID id,
            @Valid @RequestBody JobRequestDTO request) {

        return ResponseEntity.ok(
                jobService.updateJob(id, request)
        );
    }


    // ==========================================
    // DELETE / DEACTIVATE JOB
    // ==========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(
            @PathVariable UUID id) {

        jobService.deleteJob(id);

        return ResponseEntity.ok(
                "Job deleted successfully"
        );
    }


    // ==========================================
    // EMPLOYER DASHBOARD STATISTICS
    // ==========================================

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardStatistics() {

        return ResponseEntity.ok(
                new DashboardStatistics(
                        jobService.getTotalJobs(),
                        jobService.getActiveJobs()
                )
        );
    }


    // ==========================================
    // SEARCH JOBS
    // ==========================================

    @GetMapping("/search")
    public ResponseEntity<Page<JobResponseDTO>> searchJobs(

            @RequestParam(required = false)
            String keyword,

            @RequestParam(required = false)
            String location,

            @RequestParam(required = false)
            String company,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String sortDir

    ) {

        return ResponseEntity.ok(
                jobService.searchJobs(
                        keyword,
                        location,
                        company,
                        page,
                        size,
                        sortBy,
                        sortDir
                )
        );
    }


    // ==========================================
    // DASHBOARD RESPONSE
    // ==========================================

    private record DashboardStatistics(
            long totalJobs,
            long activeJobs
    ) {
    }
}