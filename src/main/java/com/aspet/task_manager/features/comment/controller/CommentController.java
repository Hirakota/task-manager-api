package com.aspet.task_manager.features.comment.controller;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.features.comment.dto.request.CommentCreateRequestDto;
import com.aspet.task_manager.features.comment.dto.response.CommentResponseDto;
import com.aspet.task_manager.features.comment.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
@RequiredArgsConstructor
@Tag(name = "Comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create(@PathVariable Long taskId, @RequestBody @Valid CommentCreateRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createByTask(taskId, dto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CommentResponseDto>> getAllByTask(@PathVariable Long taskId, @ParameterObject @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(commentService.getAllByTask(taskId, pageable));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long taskId, @PathVariable Long commentId) {
        commentService.deleteByTask(taskId, commentId);
        return ResponseEntity.noContent().build();
    }
}
