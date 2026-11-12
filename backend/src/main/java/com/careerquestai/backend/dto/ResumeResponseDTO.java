package com.careerquestai.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeResponseDTO {

    private UUID id;

    private String fileName;

    private String originalFileName;

    private String fileType;

    private Long fileSize;

    private LocalDateTime uploadedAt;

    private String message;
}