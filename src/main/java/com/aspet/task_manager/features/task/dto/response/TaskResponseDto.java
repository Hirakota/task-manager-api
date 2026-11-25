package com.aspet.task_manager.features.task.dto.response;

import com.aspet.task_manager.features.tag.dto.response.TagResponseDto;
import com.aspet.task_manager.features.task.enums.Priority;
import com.aspet.task_manager.features.task.enums.TaskStatus;
import com.aspet.task_manager.features.user.dto.response.UserResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Priority priority,
        UserResponseDto assignee,
        UserResponseDto reporter,
        Set<TagResponseDto> tags,
        Long parentTaskId,
        long subtaskCount,
        long commentCount,
        LocalDate dueDate,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
}
