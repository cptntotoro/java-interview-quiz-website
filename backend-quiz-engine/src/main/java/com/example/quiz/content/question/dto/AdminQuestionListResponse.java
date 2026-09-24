package com.example.quiz.content.question.dto;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO ответа со списком вопросов в админке
 *
 * @param uuid       UUID вопроса
 * @param topicUuid  UUID темы
 * @param topicSlug  слаг темы
 * @param slug       слаг вопроса
 * @param question   вопрос
 * @param type       тип
 * @param difficulty сложность
 * @param status     статус
 * @param createdAt  время создания
 * @param updatedAt  время обновления
 */
public record AdminQuestionListResponse(
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
