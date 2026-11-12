package com.careerquestai.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String company;

    @NotBlank
    private String location;

    private String description;

    @NotBlank
    private String employmentType;

    @NotNull
    private Double salary;

    @NotBlank
    private String experience;

    @NotBlank
    private String skills;
}