package com.example.quiz.content.question.query;

import com.example.quiz.content.question.entity.QuestionType;

import java.util.UUID;

/**
 * Запрос на предпросмотр вопрооа (для списка)
 *
 * @param uuid      UUID вопроса
 * @param topicUuid UUID темы
 * @param slug      слаг
 * @param question  вопрос
 * @param levelId   идентификатор уровня
 */
public record PublicQuestionListView(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        QuestionType type,
        Short levelId
) {
}
