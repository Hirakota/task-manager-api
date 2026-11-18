package com.aspet.task_manager.features.user.domain;

import com.aspet.task_manager.features.comment.domain.CommentEntity;
import com.aspet.task_manager.features.project.domain.ProjectEntity;
import com.aspet.task_manager.features.task.domain.TaskEntity;
import com.aspet.task_manager.features.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

    @OneToMany(mappedBy = "owner")
    @Builder.Default
    private Set<ProjectEntity> ownedProjects = new HashSet<>();

    @OneToMany(mappedBy = "assignee")
    @Builder.Default
    private List<TaskEntity> assignedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "reporter")
    @Builder.Default
    private List<TaskEntity> reportedTasks = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    @Builder.Default
    private List<CommentEntity> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    @Builder.Default
    private Set<ProjectEntity> projects = new HashSet<>();
}
