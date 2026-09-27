package com.example.quiz.content.question.query;

import com.example.quiz.content.question.entity.QuestionType;

import java.util.UUID;

/**
 * Модель публилчного содержания вопроса
 *
 * @param uuid            UUID вопроса
 * @param topicUuid       UUID темы
 * @param slug            слаг
 * @param question        вопрос
 * @param referenceAnswer ответ
 * @param explanation     объяснение
 * @param levelId         идентификатор уровня
 */
public record PublicQuestionDetailsView(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String referenceAnswer,
        String explanation,
        QuestionType type,
        Short levelId
) {
}