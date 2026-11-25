package com.aspet.task_manager.features.tag.service;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.common.exceptions.AlreadyExistException;
import com.aspet.task_manager.common.exceptions.BadRequestException;
import com.aspet.task_manager.common.exceptions.NotFoundException;
import com.aspet.task_manager.features.tag.adapter.TagMapper;
import com.aspet.task_manager.features.tag.domain.TagEntity;
import com.aspet.task_manager.features.tag.dto.request.TagCreateRequestDto;
import com.aspet.task_manager.features.tag.dto.response.TagResponseDto;
import com.aspet.task_manager.features.tag.repository.TagRepository;
import com.aspet.task_manager.features.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;


    @Transactional
    public TagResponseDto create(TagCreateRequestDto dto) {
        if (tagRepository.existsByNameIgnoreCase(dto.name())) {
            throw new AlreadyExistException("Tag with this name already exist");
        }

        TagEntity tag = TagEntity.builder().name(dto.name()).color(dto.color()).build();

        tagRepository.save(tag);

        return TagMapper.mapToDto(tag);
    }

    @Transactional(readOnly = true)
    public PageResponse<TagResponseDto> findAll(String search, Pageable pageable) {
        return PageResponse.of(tagRepository.findByNameContainingIgnoreCase(search, pageable), TagMapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public TagResponseDto getById(Long id) {
        return TagMapper.mapToDto(tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag not found: " + id)));
    }

    @Transactional
    public void deleteById(Long id) {
        TagEntity tag = tagRepository.findById(id).orElseThrow(() -> new NotFoundException("Tag not found: " + id));
        // TODO: Fix n+1
        // P.S. Is it necessary here ?
        if (!tag.getTasks().isEmpty()) throw new BadRequestException("Tag is used in tasks");
        tagRepository.delete(tag);
    }
}
