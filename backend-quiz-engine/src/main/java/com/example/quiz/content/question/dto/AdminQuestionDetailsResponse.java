package com.example.quiz.content.question.dto;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;

import java.time.Instant;
import java.util.UUID;

/**
 * DTO вопроса для детального просмотра в админке
 *
 * @param uuid        UUID вопроса
 * @param topicUuid   UUID темы
 * @param topicSlug   слаг темы
 * @param slug        слаг вопроса
 * @param question    текст вопроса
 * @param answer      эталонный ответ
 * @param explanation объяснение
 * @param type        тип вопроса
 * @param difficulty  сложность
 * @param status      статус
 * @param createdAt   время создания
 * @param updatedAt   время обновления
 * @param publishedAt время публикации
 */
public record AdminQuestionDetailsResponse(
        UUID uuid,
        UUID topicUuid,
        String topicSlug,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionType type,
        QuestionDifficulty difficulty,
        ContentStatus status,
        Instant createdAt,
        Instant updatedAt,
        Instant publishedAt
) {
}