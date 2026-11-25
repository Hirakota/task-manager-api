package com.aspet.task_manager.features.user.service;

import com.aspet.task_manager.common.dto.PageResponse;
import com.aspet.task_manager.common.exceptions.AlreadyExistException;
import com.aspet.task_manager.common.exceptions.NotFoundException;
import com.aspet.task_manager.features.user.adapter.UserMapper;
import com.aspet.task_manager.features.user.domain.UserEntity;
import com.aspet.task_manager.features.user.dto.request.UserCreateRequestDto;
import com.aspet.task_manager.features.user.dto.request.UserPatchRequestDto;
import com.aspet.task_manager.features.user.dto.response.UserResponseDto;
import com.aspet.task_manager.features.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto create(UserCreateRequestDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new AlreadyExistException("User with this email already exist");
        }

        UserEntity user = UserEntity.builder().email(dto.email()).fullName(dto.fullName()).role(dto.role()).build();

        userRepository.save(user);
        return UserMapper.mapToDto(user);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserResponseDto> getAll(Pageable pageable) {
        return PageResponse.of(userRepository.findAll(pageable), UserMapper::mapToDto);
    }

    @Transactional(readOnly = true)
    public UserResponseDto getById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));

        return UserMapper.mapToDto(user);
    }

    @Transactional
    public UserResponseDto patch(Long id, UserPatchRequestDto dto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));

        if (dto.fullName() != null) user.setFullName(dto.fullName());
        if (dto.role() != null) user.setRole(dto.role());

        return UserMapper.mapToDto(user);
    }


}
