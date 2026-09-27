package com.example.quiz.content.question.query;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionType;

import java.time.Instant;
import java.util.UUID;


/**
 * Модель вопроса для списка в админке
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
public record AdminQuestionListView(
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