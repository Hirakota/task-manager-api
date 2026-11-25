package com.aspet.task_manager.features.tag.adapter;

import com.aspet.task_manager.features.tag.domain.TagEntity;
import com.aspet.task_manager.features.tag.dto.response.TagResponseDto;

public class TagMapper {
    public static TagResponseDto mapToDto(TagEntity tag) {
        return new TagResponseDto(tag.getId(), tag.getName(), tag.getColor());
    }
}
