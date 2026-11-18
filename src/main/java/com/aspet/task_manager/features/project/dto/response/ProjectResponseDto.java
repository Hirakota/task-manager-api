package com.aspet.task_manager.features.project.dto.response;

import com.aspet.task_manager.features.user.dto.response.UserResponseDto;

import java.util.Set;

public record ProjectResponseDto(Long id, String name, String description, UserResponseDto owner,
                                 Set<UserResponseDto> members) {
}
