package com.aspet.task_manager.features.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProjectUpdateRequestDto(
        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 1000)
        String description
) {
}
