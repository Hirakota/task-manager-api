package com.aspet.task_manager.features.task.repository;

import com.aspet.task_manager.features.task.domain.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @EntityGraph(attributePaths = {"assignee", "reporter", "tags", "parentTask"})
        // hot ot create other findById that not override it
    Optional<TaskEntity> findById(Long id);

    @EntityGraph(attributePaths = {"assignee"})
    Page<TaskEntity> findByProjectId(Long id,
                                     Pageable pageable);

    @EntityGraph(attributePaths = {"assignee"})
    List<TaskEntity> findByParentTaskId(Long id);

    long countByParentTaskId(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);
}
