package com.example.quiz.content.topic.entity;

import com.example.quiz.content.common.ContentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

/**
 * Тема
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "topic")
public class Topic {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID uuid;

    @Column(nullable = false, length = 150)
    private String slug;

    @Column(nullable = false, length = 255)
    private String name;

    @Column
    private String description;

    @Column(name = "parent_id")
    private UUID parentUuid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContentStatus status;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "published_at")
    private Instant publishedAt;
}
