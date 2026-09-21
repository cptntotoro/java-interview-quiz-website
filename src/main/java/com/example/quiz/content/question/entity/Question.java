package com.example.quiz.content.question.entity;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.QuestionDifficulty;
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
 * Вопрос
 */
@Getter
@Setter
@Entity
@Table(name = "question")
@NoArgsConstructor
public class Question {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "topic_uuid", nullable = false)
    private UUID topicUuid;

    @Column(nullable = false, length = 150)
    private String slug;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    private String explanation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private QuestionDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ContentStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(name = "published_at")
    private Instant publishedAt;
}
