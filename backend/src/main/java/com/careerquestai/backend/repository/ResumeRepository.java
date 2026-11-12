package com.careerquestai.backend.repository;

import com.careerquestai.backend.entity.Resume;
import com.careerquestai.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResumeRepository extends JpaRepository<Resume, UUID> {

    Optional<Resume> findByUser(User user);

    Optional<Resume> findByUserAndActiveTrue(User user);

    boolean existsByUser(User user);
}