package com.aspet.task_manager.features.user.dto.request;

import com.aspet.task_manager.features.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequestDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255)
        String email,

        @NotBlank(message = "Full name is required")
        @Size(max = 100)
        String fullName,

        @NotNull(message = "Role is required")
        UserRole role
) {}
