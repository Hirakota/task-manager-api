package com.aspet.task_manager.features.user.adapter;

import com.aspet.task_manager.features.user.domain.UserEntity;
import com.aspet.task_manager.features.user.dto.response.UserResponseDto;

public class UserMapper {
    public static UserResponseDto mapToDto(UserEntity user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getFullName(), user.getRole());
    }
}
