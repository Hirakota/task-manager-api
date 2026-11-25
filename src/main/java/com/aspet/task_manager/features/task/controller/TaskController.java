package com.aspet.task_manager.features.task.controller;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.features.task.dto.request.TaskCreateRequestDto;
import com.aspet.task_manager.features.task.dto.response.TaskResponseDto;
import com.aspet.task_manager.features.task.dto.response.TaskSummaryResponseDto;
import com.aspet.task_manager.features.task.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponseDto> create(@PathVariable Long projectId, @RequestBody @Valid TaskCreateRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(projectId, dto));
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<PageResponse<TaskSummaryResponseDto>> getAllTasksSummaryByProjectId(@PathVariable Long projectId, @ParameterObject @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(taskService.getAllTasksSummaryByProjectId(projectId, pageable));
    }

    @GetMapping("/tasks/{taskId}/subtasks")
    public ResponseEntity<List<TaskSummaryResponseDto>> getAllSubtasksSummaryByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getAllSubtasksSummaryByTaskId(taskId));
    }
}
