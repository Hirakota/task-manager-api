package com.aspet.task_manager.features.tag.domain;

import com.aspet.task_manager.features.task.domain.TaskEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    @Builder.Default
    private Set<TaskEntity> tasks = new HashSet<>();

    @Column(length = 7)
    private String color;
}
