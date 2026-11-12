package com.careerquestai.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequestDTO {

    @NotNull(message = "Job ID is required")
    private UUID jobId;

}