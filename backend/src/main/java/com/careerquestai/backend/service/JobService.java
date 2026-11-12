package com.careerquestai.backend.service;

import com.careerquestai.backend.dto.JobRequestDTO;
import com.careerquestai.backend.dto.JobResponseDTO;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

public interface JobService {

    JobResponseDTO createJob(JobRequestDTO request);

    List<JobResponseDTO> getAllJobs();

    JobResponseDTO getJobById(UUID id);

    JobResponseDTO updateJob(UUID id, JobRequestDTO request);

    void deleteJob(UUID id);


    // ==============================
    // Employer Dashboard
    // ==============================

    long getTotalJobs();

    long getActiveJobs();


    // ==============================
    // Job Seeker Search
    // ==============================

    Page<JobResponseDTO> searchJobs(
            String keyword,
            String location,
            String company,
            int page,
            int size,
            String sortBy,
            String sortDir
    );
}