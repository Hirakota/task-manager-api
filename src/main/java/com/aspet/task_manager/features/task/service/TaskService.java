package com.aspet.task_manager.features.task.service;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.common.exceptions.BadRequestException;
import com.aspet.task_manager.common.exceptions.NotFoundException;
import com.aspet.task_manager.features.comment.repository.CommentRepository;
import com.aspet.task_manager.features.project.domain.ProjectEntity;
import com.aspet.task_manager.features.project.repository.ProjectRepository;
import com.aspet.task_manager.features.tag.domain.TagEntity;
import com.aspet.task_manager.features.tag.repository.TagRepository;
import com.aspet.task_manager.features.task.adapter.TaskMapper;
import com.aspet.task_manager.features.task.domain.TaskEntity;
import com.aspet.task_manager.features.task.dto.request.TaskCreateRequestDto;
import com.aspet.task_manager.features.task.dto.response.TaskResponseDto;
import com.aspet.task_manager.features.task.dto.response.TaskSummaryResponseDto;
import com.aspet.task_manager.features.task.enums.TaskStatus;
import com.aspet.task_manager.features.task.repository.TaskRepository;
import com.aspet.task_manager.features.user.domain.UserEntity;
import com.aspet.task_manager.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public TaskResponseDto create(Long projectId, TaskCreateRequestDto dto) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project", projectId));
        UserEntity reporter = userRepository.findById(dto.reporterId()).orElseThrow(() -> new NotFoundException("Reporter", dto.reporterId()));

        if (project.getMembers().stream().noneMatch((m -> m.getId().equals(reporter.getId())))) {
            throw new BadRequestException("Reporter is not a member");
        }

        UserEntity assignee = dto.assigneeId() != null
                ? userRepository.findById(dto.assigneeId())
                .orElseThrow(() -> new NotFoundException("Assignee", dto.assigneeId()))
                : null;

        if (assignee != null && project.getMembers().stream().noneMatch((m -> m.getId().equals(assignee.getId())))) {
            throw new BadRequestException("Assignee is not a member");
        }

        List<Long> tagIds = dto.tagIds() == null ? List.of() : dto.tagIds();
        Set<Long> uniqueTagIds = new HashSet<>(tagIds);
        List<TagEntity> found = tagRepository.findAllById(uniqueTagIds);

        if (found.size() != uniqueTagIds.size()) {
            for (Long tagId : uniqueTagIds) {
                if (found.stream().anyMatch(t -> t.getId().equals(tagId))) continue;
                throw new NotFoundException("Tag", tagId);
            }
        }

        TaskEntity parentTask = dto.parentTaskId() != null
                ? taskRepository.findById(dto.parentTaskId()).orElseThrow(() -> new NotFoundException("Parent task", dto.parentTaskId()))
                : null;

        if (parentTask != null) {
            if (parentTask.getParentTask() != null) {
                throw new BadRequestException("Parent task should not have parent task");
            }
            if (!parentTask.getProject().getId().equals(projectId)) {
                throw new BadRequestException("Parent task should be in same project");
            }
        }

        TaskEntity task = TaskEntity.builder()
                .title(dto.title())
                .description(dto.description())
                .status(TaskStatus.TODO)
                .priority(dto.priority())
                .project(project)
                .assignee(assignee)
                .reporter(reporter)
                .tags(new HashSet<>(found))
                .parentTask(parentTask)
                .dueDate(dto.dueDate())
                .build();

        taskRepository.save(task);

        return TaskMapper.mapToDto(task, 0, 0);
    }

    @Transactional(readOnly = true)
    public TaskResponseDto getTaskById(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task", taskId));

        long subtaskCount = taskRepository.countByParentTaskId(task.getId());
        long commentCount = commentRepository.countByTaskId(task.getId());

        return TaskMapper.mapToDto(task, subtaskCount, commentCount);
    }

    @Transactional(readOnly = true)
    public PageResponse<TaskSummaryResponseDto> getAllTasksSummaryByProjectId(Long projectId, Pageable pageable) {
        if (!projectRepository.existsById(projectId)) {
            throw new NotFoundException("Project", projectId);
        }

        return PageResponse.of(taskRepository.findByProjectId(projectId, pageable), TaskMapper::mapToSummaryDto);
    }

    @Transactional(readOnly = true)
    public List<TaskSummaryResponseDto> getAllSubtasksSummaryByTaskId(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new NotFoundException("Parent task", taskId);
        }

        return taskRepository.findByParentTaskId(taskId).stream().map(TaskMapper::mapToSummaryDto).toList();
    }
}
