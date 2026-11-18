package com.aspet.task_manager.features.project.controller;

import com.aspet.task_manager.features.project.dto.request.ProjectCreateRequestDto;
import com.aspet.task_manager.features.project.dto.request.ProjectUpdateRequestDto;
import com.aspet.task_manager.features.project.dto.response.ProjectResponseDto;
import com.aspet.task_manager.features.project.dto.response.ProjectSummaryResponseDto;
import com.aspet.task_manager.features.project.service.ProjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<ProjectSummaryResponseDto>> getAll(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(projectService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDto> update(@PathVariable Long id, @RequestBody @Valid ProjectUpdateRequestDto dto) {
        return ResponseEntity.ok(projectService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members/{userId}")
    public ResponseEntity<ProjectResponseDto> addMember(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.addMember(id, userId));
    }

    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<ProjectResponseDto> removeMember(@PathVariable Long id, @PathVariable Long userId) {
        return ResponseEntity.ok(projectService.removeMember(id, userId));
    }
}
