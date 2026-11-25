package com.aspet.task_manager.features.task.dto.request;

import com.aspet.task_manager.features.task.enums.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record TaskCreateRequestDto(
        @NotBlank @Size(max = 200) String title,
        @Size(max = 2000) String description,
        @NotNull Priority priority,
        @NotNull Long reporterId,
        Long assigneeId,
        @FutureOrPresent LocalDate dueDate,
        List<@NotNull Long> tagIds,
        Long parentTaskId
) {
}
