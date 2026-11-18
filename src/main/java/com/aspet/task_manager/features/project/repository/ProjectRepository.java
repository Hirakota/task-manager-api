package com.aspet.task_manager.features.project.repository;

import com.aspet.task_manager.features.project.domain.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findByOwnerId(Long id);
}
