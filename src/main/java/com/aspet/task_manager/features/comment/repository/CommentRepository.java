package com.aspet.task_manager.features.comment.repository;

import com.aspet.task_manager.features.comment.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByTaskIdOrderByCreatedAtDesc(Long taskId);
}
