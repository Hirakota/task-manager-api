package com.aspet.task_manager.features.user.dto.request;

import com.aspet.task_manager.features.user.enums.UserRole;
import jakarta.validation.constraints.Size;

public record UserPatchRequestDto(
        @Size(min = 1, max = 100) String fullName,
        UserRole role
) {}
