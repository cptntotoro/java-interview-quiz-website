package com.example.quiz.content.question.query;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;

import java.time.Instant;
import java.util.UUID;

/**
 * Модель вопроса для списка в админке
 */
public record AdminQuestionListView(
        UUID uuid,
        UUID topicUuid,
        String topicSlug,
        String slug,
        String question,
        QuestionType type,
        QuestionDifficulty difficulty,
        ContentStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}