package com.aspet.task_manager.features.task.dto.response;

import com.aspet.task_manager.features.task.enums.Priority;
import com.aspet.task_manager.features.task.enums.TaskStatus;
import com.aspet.task_manager.features.user.dto.response.UserResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskSummaryResponseDto(
        Long id,
        String title,
        TaskStatus status,
        Priority priority,
        UserResponseDto assignee,
        LocalDate dueDate,
        LocalDateTime createdAt
) {
}
