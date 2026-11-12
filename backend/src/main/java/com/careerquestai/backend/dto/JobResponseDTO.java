package com.careerquestai.backend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDTO {

    private UUID id;

    private String title;

    private String company;

    private String location;

    private String description;

    private String employmentType;

    private Double salary;

    private String experience;

    private String skills;

    private Boolean active;

    private LocalDateTime createdAt;
}