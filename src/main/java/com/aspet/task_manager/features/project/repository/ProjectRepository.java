package com.aspet.task_manager.features.project.repository;

import com.aspet.task_manager.features.project.domain.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByOwnerId(Long id);

    @EntityGraph(attributePaths = {"owner"})
    Page<ProjectEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"owner", "members"})
    Optional<ProjectEntity> findById(Long id);

    boolean existsByIdAndTasksIsNotEmpty(Long id);
}
