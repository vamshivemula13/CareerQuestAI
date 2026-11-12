package com.careerquestai.backend.service.impl;

import com.careerquestai.backend.dto.JobRequestDTO;
import com.careerquestai.backend.dto.JobResponseDTO;
import com.careerquestai.backend.entity.Job;
import com.careerquestai.backend.entity.User;
import com.careerquestai.backend.repository.JobRepository;
import com.careerquestai.backend.repository.UserRepository;
import com.careerquestai.backend.service.JobService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;


    // ==========================================
    // CREATE JOB
    // ==========================================

    @Override
    public JobResponseDTO createJob(JobRequestDTO request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("User is not authenticated");
        }

        String email = authentication.getName();

        User employer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employer not found: " + email
                        )
                );

        Job job = Job.builder()
                .employer(employer)
                .title(request.getTitle())
                .company(request.getCompany())
                .location(request.getLocation())
                .description(request.getDescription())
                .employmentType(request.getEmploymentType())
                .salary(request.getSalary())
                .experience(request.getExperience())
                .skills(request.getSkills())
                .active(true)
                .build();

        job = jobRepository.save(job);

        return mapToResponse(job);
    }


    // ==========================================
    // GET EMPLOYER'S ACTIVE JOBS
    // ==========================================

    @Override
    public List<JobResponseDTO> getAllJobs() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("User is not authenticated");
        }

        String email = authentication.getName();

        User employer = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employer not found: " + email
                        )
                );

        return jobRepository.findByEmployerAndActiveTrue(employer)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    // ==========================================
    // DASHBOARD - TOTAL JOBS
    // ==========================================

    @Override
    public long getTotalJobs() {

        User employer = getAuthenticatedEmployer();

        return jobRepository.countByEmployer(employer);
    }


    // ==========================================
    // DASHBOARD - ACTIVE JOBS
    // ==========================================

    @Override
    public long getActiveJobs() {

        User employer = getAuthenticatedEmployer();

        return jobRepository.countByEmployerAndActiveTrue(employer);
    }


    // ==========================================
    // GET JOB BY ID
    // ==========================================

    @Override
    public JobResponseDTO getJobById(UUID id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        return mapToResponse(job);
    }


    // ==========================================
    // UPDATE JOB
    // ==========================================

    @Override
    public JobResponseDTO updateJob(
            UUID id,
            JobRequestDTO request) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setDescription(request.getDescription());
        job.setEmploymentType(request.getEmploymentType());
        job.setSalary(request.getSalary());
        job.setExperience(request.getExperience());
        job.setSkills(request.getSkills());

        job = jobRepository.save(job);

        return mapToResponse(job);
    }


    // ==========================================
    // DELETE JOB
    // ==========================================

    @Override
    public void deleteJob(UUID id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        job.setActive(false);

        jobRepository.save(job);
    }


    // ==========================================
    // SEARCH JOBS
    // ==========================================

    @Override
    public Page<JobResponseDTO> searchJobs(
            String keyword,
            String location,
            String company,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<Job> jobs;

        if (keyword != null && !keyword.isBlank()
                && location != null && !location.isBlank()) {

            jobs = jobRepository
                    .findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
                            keyword,
                            location,
                            pageable
                    );

        } else if (keyword != null && !keyword.isBlank()) {

            jobs = jobRepository
                    .findByTitleContainingIgnoreCase(
                            keyword,
                            pageable
                    );

        } else if (location != null && !location.isBlank()) {

            jobs = jobRepository
                    .findByLocationContainingIgnoreCase(
                            location,
                            pageable
                    );

        } else if (company != null && !company.isBlank()) {

            jobs = jobRepository
                    .findByCompanyContainingIgnoreCase(
                            company,
                            pageable
                    );

        } else {

            jobs = jobRepository.findAll(pageable);
        }

        return jobs.map(this::mapToResponse);
    }


    // ==========================================
    // GET AUTHENTICATED EMPLOYER
    // ==========================================

    private User getAuthenticatedEmployer() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employer not found: " + email
                        )
                );
    }


    // ==========================================
    // MAP ENTITY → RESPONSE DTO
    // ==========================================

    private JobResponseDTO mapToResponse(Job job) {

        return JobResponseDTO.builder()
                .id(job.getId())
                .title(job.getTitle())
                .company(job.getCompany())
                .location(job.getLocation())
                .description(job.getDescription())
                .employmentType(job.getEmploymentType())
                .salary(job.getSalary())
                .experience(job.getExperience())
                .skills(job.getSkills())
                .active(job.getActive())
                .createdAt(job.getCreatedAt())
                .build();
    }
}