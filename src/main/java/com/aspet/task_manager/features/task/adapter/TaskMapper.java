package com.aspet.task_manager.features.task.adapter;

import com.aspet.task_manager.features.tag.adapter.TagMapper;
import com.aspet.task_manager.features.task.domain.TaskEntity;
import com.aspet.task_manager.features.task.dto.response.TaskResponseDto;
import com.aspet.task_manager.features.task.dto.response.TaskSummaryResponseDto;
import com.aspet.task_manager.features.user.adapter.UserMapper;

import java.util.stream.Collectors;

public class TaskMapper {
    public static TaskSummaryResponseDto mapToSummaryDto(TaskEntity task) {
        return new TaskSummaryResponseDto(
                task.getId(),
                task.getTitle(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignee() == null ? null : UserMapper.mapToDto(task.getAssignee()),
                task.getDueDate(),
                task.getCreatedAt()
        );
    }

    public static TaskResponseDto mapToDto(TaskEntity task, long subtaskCount, long commentCount) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignee() == null ? null : UserMapper.mapToDto(task.getAssignee()),
                task.getReporter() == null ? null : UserMapper.mapToDto(task.getReporter()),
                task.getTags().stream().map(TagMapper::mapToDto).collect(Collectors.toSet()),
                task.getParentTask() == null ? null : task.getParentTask().getId(),
                subtaskCount,
                commentCount,
                task.getDueDate(),
                task.getCreatedAt(),
                task.getCompletedAt()
        );
    }
}
