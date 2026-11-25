package com.aspet.task_manager.features.project.controller;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.features.project.dto.request.ProjectCreateRequestDto;
import com.aspet.task_manager.features.project.dto.request.ProjectUpdateRequestDto;
import com.aspet.task_manager.features.project.dto.response.ProjectResponseDto;
import com.aspet.task_manager.features.project.dto.response.ProjectSummaryResponseDto;
import com.aspet.task_manager.features.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Tag(name = "Projects")
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> create(@RequestBody @Valid ProjectCreateRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(dto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ProjectSummaryResponseDto>> getAll(@ParameterObject @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(projectService.getAll(pageable));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDto> getById(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getById(projectId));
    }

    @PutMapping("/{projectId}")
    @Operation(description = "Update project name and description")
    public ResponseEntity<ProjectResponseDto> update(@PathVariable Long projectId, @RequestBody @Valid ProjectUpdateRequestDto dto) {
        return ResponseEntity.ok(projectService.update(projectId, dto));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> delete(@PathVariable Long projectId) {
        projectService.delete(projectId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projectId}/members/{userId}")
    public ResponseEntity<ProjectResponseDto> addMember(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.addMember(projectId, userId));
    }

    @DeleteMapping("/{projectId}/members/{userId}")
    public ResponseEntity<ProjectResponseDto> removeMember(@PathVariable Long projectId, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.removeMember(projectId, userId));
    }
}
