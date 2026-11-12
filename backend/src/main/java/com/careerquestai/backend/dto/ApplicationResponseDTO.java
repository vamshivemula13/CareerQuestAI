package com.careerquestai.backend.dto;

import com.careerquestai.backend.enums.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDTO {  

    private UUID applicationId;

    private UUID jobId;
    private String jobTitle;

    private String company;

    private UUID userId;
    private String applicantName;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;

}