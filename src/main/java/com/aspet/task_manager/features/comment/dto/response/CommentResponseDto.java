package com.aspet.task_manager.features.comment.dto.response;

import com.aspet.task_manager.features.user.dto.response.UserResponseDto;

import java.time.LocalDateTime;

public record CommentResponseDto(Long id, String text, UserResponseDto author, LocalDateTime createdAt) {
}
