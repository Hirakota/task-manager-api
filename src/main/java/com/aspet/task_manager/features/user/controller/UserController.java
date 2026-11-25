package com.aspet.task_manager.features.user.controller;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.features.user.dto.request.UserCreateRequestDto;
import com.aspet.task_manager.features.user.dto.request.UserPatchRequestDto;
import com.aspet.task_manager.features.user.dto.response.UserResponseDto;
import com.aspet.task_manager.features.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody @Valid UserCreateRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserResponseDto>> getAll(
            @ParameterObject @PageableDefault(size = 20, sort = "fullName") Pageable pageable
    ) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponseDto> patch(
            @PathVariable Long userId,
            @RequestBody @Valid UserPatchRequestDto dto
    ) {
        return ResponseEntity.ok(userService.patch(userId, dto));
    }
}
