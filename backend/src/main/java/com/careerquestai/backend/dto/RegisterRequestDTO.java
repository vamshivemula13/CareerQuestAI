package com.careerquestai.backend.dto;

import com.careerquestai.backend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(
            min = 6,
            message = "Password must contain minimum 6 characters"
    )
    private String password;

    @NotBlank(message = "Phone number is required")
    private String phone;

    private String company;

    private Role role;
}