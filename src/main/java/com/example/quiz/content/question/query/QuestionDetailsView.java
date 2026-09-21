package com.example.quiz.content.question.query;

import com.example.quiz.content.question.QuestionDifficulty;

import java.util.UUID;

/**
 * Содержание вопроса
 *
 * @param uuid UUID вопроса
 * @param topicUuid UUID темы
 * @param slug слаг
 * @param question вопрос
 * @param answer ответ
 * @param explanation объяснение
 * @param difficulty сложность
 */
public record QuestionDetailsView(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionDifficulty difficulty
) {
}