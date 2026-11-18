package com.aspet.task_manager.features.project.adapter;

import com.aspet.task_manager.features.project.domain.ProjectEntity;
import com.aspet.task_manager.features.project.dto.response.ProjectResponseDto;
import com.aspet.task_manager.features.project.dto.response.ProjectSummaryResponseDto;
import com.aspet.task_manager.features.user.adapter.UserMapper;
import com.aspet.task_manager.features.user.dto.response.UserResponseDto;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectMapper {
    public static ProjectSummaryResponseDto mapToSummaryDto(ProjectEntity project) {
        return new ProjectSummaryResponseDto(
                project.getId(),
                project.getName(),
                UserMapper.mapToDto(project.getOwner())
        );
    }

    public static ProjectResponseDto mapToDto(ProjectEntity project) {
        Set<UserResponseDto> members = project.getMembers().stream().map(UserMapper::mapToDto).collect(Collectors.toSet());
        return new ProjectResponseDto(
                project.getId(),
                project.getName(),
                project.getDescription(),
                UserMapper.mapToDto(project.getOwner()),
                members
        );
    }
}
