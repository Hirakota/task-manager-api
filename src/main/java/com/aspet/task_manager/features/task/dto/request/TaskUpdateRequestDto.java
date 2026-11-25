package com.aspet.task_manager.features.task.dto.request;

import com.aspet.task_manager.features.task.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskUpdateRequestDto(
        @NotBlank @Size(max = 200) String title,
        @Size(max = 2000) String description,
        @NotNull Priority priority,
        Long assigneeId,
        LocalDate dueDate
) {
}
