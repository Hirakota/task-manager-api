package com.aspet.task_manager.features.project.service;

import com.aspet.task_manager.common.exceptions.AlreadyExistException;
import com.aspet.task_manager.common.exceptions.BadRequestException;
import com.aspet.task_manager.common.exceptions.NotFoundException;
import com.aspet.task_manager.features.project.adapter.ProjectMapper;
import com.aspet.task_manager.features.project.domain.ProjectEntity;
import com.aspet.task_manager.features.project.dto.request.ProjectCreateRequestDto;
import com.aspet.task_manager.features.project.dto.request.ProjectUpdateRequestDto;
import com.aspet.task_manager.features.project.dto.response.ProjectResponseDto;
import com.aspet.task_manager.features.project.dto.response.ProjectSummaryResponseDto;
import com.aspet.task_manager.features.project.repository.ProjectRepository;
import com.aspet.task_manager.features.user.domain.UserEntity;
import com.aspet.task_manager.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponseDto create(ProjectCreateRequestDto dto) {
        UserEntity owner = userRepository.findById(dto.ownerId()).orElseThrow(() -> new NotFoundException("Owner not found: " + dto.ownerId()));

        ProjectEntity project = ProjectEntity.builder().name(dto.name()).description(dto.description()).owner(owner).build();

        project.getMembers().add(owner);

        projectRepository.save(project);
        return ProjectMapper.mapToDto(project);
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getById(Long id) {
        ProjectEntity project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found: " + id));

        return ProjectMapper.mapToDto(project);
    }

    @Transactional
    public ProjectResponseDto update(Long id, ProjectUpdateRequestDto dto) {
        ProjectEntity project = projectRepository.findById(id).orElseThrow(() -> new NotFoundException("Project not found: " + id));

        project.setName(dto.name());
        project.setDescription(dto.description());

        return ProjectMapper.mapToDto(project);
    }


    @Transactional
    public void delete(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new NotFoundException("Project not found: " + id);
        }

        if (projectRepository.existsByIdAndTasksIsNotEmpty(id)) {
            throw new BadRequestException("Cannot delete project with tasks");
        }

        projectRepository.deleteById(id);
    }

    @Transactional
    public ProjectResponseDto addMember(Long projectId, Long userId) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found: " + projectId));
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found: " + userId));

        boolean alreadyMember = project.getMembers().stream().anyMatch(u -> u.getId().equals(userId));

        if (alreadyMember) {
            throw new AlreadyExistException("User already is member: " + userId);
        }

        project.getMembers().add(user);

        return ProjectMapper.mapToDto(project);
    }

    @Transactional
    public ProjectResponseDto removeMember(Long projectId, Long userId) {
        ProjectEntity project = projectRepository.findById(projectId).orElseThrow(() -> new NotFoundException("Project not found: " + projectId));

        if (project.getOwner().getId().equals(userId)) {
            throw new BadRequestException("Cannot remove owner");
        }

        boolean removed = project.getMembers().removeIf(u -> u.getId().equals(userId));

        if (!removed) {
            throw new NotFoundException("User " + userId + " is not a member of this project");
        }

        return ProjectMapper.mapToDto(project);
    }

    @Transactional(readOnly = true)
    public Page<ProjectSummaryResponseDto> getAll(Pageable pageable) {
        return projectRepository.findAll(pageable).map(ProjectMapper::mapToSummaryDto);
    }
}
