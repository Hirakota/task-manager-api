package com.aspet.task_manager.features.task.repository;

import com.aspet.task_manager.features.task.domain.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByProjectId(Long id);
    List<TaskEntity> findByAssigneeId(Long id);
    List<TaskEntity> findByParentTaskId(Long id);
}
