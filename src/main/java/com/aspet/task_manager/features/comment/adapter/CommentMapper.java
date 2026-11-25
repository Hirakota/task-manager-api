package com.aspet.task_manager.features.comment.adapter;

import com.aspet.task_manager.features.comment.domain.CommentEntity;
import com.aspet.task_manager.features.comment.dto.response.CommentResponseDto;
import com.aspet.task_manager.features.user.adapter.UserMapper;

public class CommentMapper {
    public static CommentResponseDto mapToDto(CommentEntity comment) {
        return new CommentResponseDto(comment.getId(), comment.getText(), UserMapper.mapToDto(comment.getAuthor()), comment.getCreatedAt());
    }
}
