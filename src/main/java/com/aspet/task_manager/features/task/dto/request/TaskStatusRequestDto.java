package com.aspet.task_manager.features.task.dto.request;

import com.aspet.task_manager.features.task.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record TaskStatusRequestDto(@NotNull TaskStatus status) {
}
