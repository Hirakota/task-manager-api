package com.aspet.task_manager.features.comment.service;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.common.exceptions.BadRequestException;
import com.aspet.task_manager.common.exceptions.NotFoundException;
import com.aspet.task_manager.features.comment.adapter.CommentMapper;
import com.aspet.task_manager.features.comment.domain.CommentEntity;
import com.aspet.task_manager.features.comment.dto.request.CommentCreateRequestDto;
import com.aspet.task_manager.features.comment.dto.response.CommentResponseDto;
import com.aspet.task_manager.features.comment.repository.CommentRepository;
import com.aspet.task_manager.features.task.domain.TaskEntity;
import com.aspet.task_manager.features.task.enums.TaskStatus;
import com.aspet.task_manager.features.task.repository.TaskRepository;
import com.aspet.task_manager.features.user.domain.UserEntity;
import com.aspet.task_manager.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;


    @Transactional
    public CommentResponseDto createByTask(Long taskId, CommentCreateRequestDto dto) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new NotFoundException("Task", taskId));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new BadRequestException("Cannot comment on a DONE task");
        }

        UserEntity author = userRepository.findById(dto.authorId()).orElseThrow(() -> new NotFoundException("User", dto.authorId()));

        CommentEntity comment = CommentEntity.builder()
                .author(author)
                .task(task)
                .text(dto.text())
                .build();

        commentRepository.save(comment);

        return CommentMapper.mapToDto(comment);
    }

    @Transactional(readOnly = true)
    public PageResponse<CommentResponseDto> getAllByTask(Long taskId, Pageable pageable) {
        if (!taskRepository.existsById(taskId)) {
            throw new NotFoundException("Task", taskId);
        }

        return PageResponse.of(commentRepository.findByTaskIdOrderByCreatedAtDesc(taskId, pageable), CommentMapper::mapToDto);
    }

    @Transactional
    public void deleteByTask(Long taskId, Long commentId) {
        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Comment", commentId));

        if (!comment.getTask().getId().equals(taskId)) {
            throw new NotFoundException("Comment " + commentId + " does not belong to task " + taskId);
        }

        commentRepository.delete(comment);
    }

}
