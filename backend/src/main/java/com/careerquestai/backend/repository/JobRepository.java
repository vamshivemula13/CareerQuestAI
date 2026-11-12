package com.careerquestai.backend.repository;

import com.careerquestai.backend.entity.Job;
import com.careerquestai.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    // ==============================
    // General / Job Seeker
    // ==============================

    List<Job> findByActiveTrue();


    // ==============================
    // Employer-specific jobs
    // ==============================

    // All jobs created by a particular employer
    List<Job> findByEmployer(User employer);

    // Only active jobs created by a particular employer
    List<Job> findByEmployerAndActiveTrue(User employer);

    // Count all jobs created by a particular employer
    long countByEmployer(User employer);

    // Count active jobs created by a particular employer
    long countByEmployerAndActiveTrue(User employer);


    // ==============================
    // Job Seeker Search
    // ==============================

    Page<Job> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );

    Page<Job> findByTitleContainingIgnoreCaseAndLocationContainingIgnoreCase(
            String title,
            String location,
            Pageable pageable
    );

    Page<Job> findByLocationContainingIgnoreCase(
            String location,
            Pageable pageable
    );

    Page<Job> findByCompanyContainingIgnoreCase(
            String company,
            Pageable pageable
    );
}