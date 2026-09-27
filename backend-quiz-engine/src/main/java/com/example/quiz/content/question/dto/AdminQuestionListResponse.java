package com.example.quiz.content.question.dto;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO ответа со списком вопросов в админке
 *
 * @param uuid      UUID вопроса
 * @param topicUuid UUID темы
 * @param topicSlug слаг темы
 * @param slug      слаг вопроса
 * @param question  вопрос
 * @param type      тип
 * @param levelId   идентификатор уровня
 * @param status    статус
 * @param createdAt время создания
 * @param updatedAt время обновления
 */
@Builder
public record AdminQuestionListResponse(
        UUID uuid,
        UUID topicUuid,
        String topicSlug,
        String slug,
        String question,
        QuestionType type,
        Short levelId,
        ContentStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
