package com.example.quiz.content.question.query;

import com.example.quiz.content.question.QuestionDifficulty;

import java.util.UUID;

/**
 * Запрос на предпросмотр вопрооа (для списка)
 *
 * @param uuid UUID вопроса
 * @param topicUuid UUID темы
 * @param slug слаг
 * @param question вопрос
 * @param difficulty сложность
 */
public record QuestionListView(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        QuestionDifficulty difficulty
) {
}
