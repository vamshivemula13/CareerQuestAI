package com.careerquestai.backend.service.impl;

import com.careerquestai.backend.dto.ApplicationRequestDTO;
import com.careerquestai.backend.dto.ApplicationResponseDTO;
import com.careerquestai.backend.entity.Application;
import com.careerquestai.backend.entity.Job;
import com.careerquestai.backend.entity.Resume;
import com.careerquestai.backend.entity.User;
import com.careerquestai.backend.repository.ApplicationRepository;
import com.careerquestai.backend.repository.JobRepository;
import com.careerquestai.backend.repository.ResumeRepository;
import com.careerquestai.backend.repository.UserRepository;
import com.careerquestai.backend.service.ApplicationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;

    @Override
    public ApplicationResponseDTO applyForJob(
            ApplicationRequestDTO request,
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Job job = jobRepository.findById(request.getJobId())
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        Resume resume = resumeRepository.findByUserAndActiveTrue(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Please upload your resume before applying"));

        if (applicationRepository.existsByUserAndJob(user, job)) {
            throw new RuntimeException(
                    "You have already applied for this job");
        }

        Application application = Application.builder()
                .user(user)
                .job(job)
                .resume(resume)
                .build();

        application = applicationRepository.save(application);

        return mapToResponse(application);
    }

    @Override
    public List<ApplicationResponseDTO> getMyApplications(
            String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return applicationRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ApplicationResponseDTO> getApplicationsByJob(
            UUID jobId,
            String employerEmail) {

        User employer = userRepository.findByEmail(employerEmail)
                .orElseThrow(() ->
                        new RuntimeException("Employer not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (job.getEmployer() == null ||
                !job.getEmployer().getId().equals(employer.getId())) {

            throw new RuntimeException(
                    "You are not authorized to view applications for this job");
        }

        return applicationRepository.findByJob(job)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ApplicationResponseDTO mapToResponse(
            Application application) {

        return ApplicationResponseDTO.builder()
                .applicationId(application.getId())
                .jobId(application.getJob().getId())
                .jobTitle(application.getJob().getTitle())
                .company(application.getJob().getCompany())
                .userId(application.getUser().getId())
                .applicantName(application.getUser().getFullName())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }
}