package com.aspet.task_manager.features.project.dto.response;

import com.aspet.task_manager.features.user.dto.response.UserResponseDto;

public record ProjectSummaryResponseDto(Long id, String name, UserResponseDto owner) {
}
