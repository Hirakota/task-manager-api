package com.aspet.task_manager.features.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateRequestDto(@NotBlank @Size(max = 1000) String text, @NotNull Long authorId) {
}
