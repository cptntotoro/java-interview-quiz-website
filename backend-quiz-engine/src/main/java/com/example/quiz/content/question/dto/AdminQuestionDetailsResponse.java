package com.example.quiz.content.question.dto;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO вопроса для детального просмотра в админке
 *
 * @param uuid            UUID вопроса
 * @param topicUuid       UUID темы
 * @param topicSlug       слаг темы
 * @param slug            слаг вопроса
 * @param question        текст вопроса
 * @param referenceAnswer эталонный ответ
 * @param explanation     объяснение
 * @param type            тип вопроса
 * @param levelId         идентификатор уровня
 * @param status          статус
 * @param createdAt       время создания
 * @param updatedAt       время обновления
 * @param publishedAt     время публикации
 */
@Builder
public record AdminQuestionDetailsResponse(
        UUID uuid,
        UUID topicUuid,
        String topicSlug,
        String slug,
        String question,
        String referenceAnswer,
        String explanation,
        QuestionType type,
        Short levelId,
        ContentStatus status,
        Instant createdAt,
        Instant updatedAt,
        Instant publishedAt
) {
}