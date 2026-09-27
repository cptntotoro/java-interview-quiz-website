package com.example.quiz.content.question.dto;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO ответа с полным вопросом
 *
 * @param uuid
 * @param topicUuid
 * @param slug            слаг
 * @param question        вопрос
 * @param referenceAnswer ответ
 * @param explanation     объяснение
 * @param levelId         идентификатор уровня
 * @param type            тип
 * @param status          статус
 */
@Builder
public record AdminQuestionResponse(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String referenceAnswer,
        String explanation,
        Short levelId,
        QuestionType type,
        ContentStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
