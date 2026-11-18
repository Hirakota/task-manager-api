package com.aspet.task_manager.features.tag.repository;

import com.aspet.task_manager.features.tag.domain.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
