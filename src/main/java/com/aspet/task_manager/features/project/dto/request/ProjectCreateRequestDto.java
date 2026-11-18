package com.aspet.task_manager.features.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectCreateRequestDto(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 1000)
        String description,

        @NotNull(message = "ownerId is required")
        Long ownerId
) {
}
