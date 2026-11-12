package com.careerquestai.backend.repository;

import com.careerquestai.backend.entity.Application;
import com.careerquestai.backend.entity.Job;
import com.careerquestai.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    List<Application> findByUser(User user);

    List<Application> findByJob(Job job);

    Optional<Application> findByUserAndJob(User user, Job job);

    boolean existsByUserAndJob(User user, Job job);
}