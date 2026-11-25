package com.aspet.task_manager.features.tag.controller;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.features.tag.dto.request.TagCreateRequestDto;
import com.aspet.task_manager.features.tag.dto.response.TagResponseDto;
import com.aspet.task_manager.features.tag.service.TagService;
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
@RequestMapping("/api/tags")
@Tag(name = "Tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponseDto> create(@RequestBody @Valid TagCreateRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.create(dto));
    }

    @GetMapping
    public ResponseEntity<PageResponse<TagResponseDto>> findAll(
            @RequestParam(required = false) String search,
            @ParameterObject @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return ResponseEntity.ok(tagService.findAll(search, pageable));
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagResponseDto> getById(@PathVariable Long tagId) {
        return ResponseEntity.ok(tagService.getById(tagId));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long tagId) {
        tagService.deleteById(tagId);
        return ResponseEntity.noContent().build();
    }
}
