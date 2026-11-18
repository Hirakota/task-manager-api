package com.aspet.task_manager.features.user.dto.response;

import com.aspet.task_manager.features.user.enums.UserRole;

public record UserResponseDto(
        Long id, String email, String fullName, UserRole role
) {
}
