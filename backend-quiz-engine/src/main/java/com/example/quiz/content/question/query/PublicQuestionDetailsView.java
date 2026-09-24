package com.example.quiz.content.question.query;

import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;

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
public record PublicQuestionDetailsView(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionType type,
        QuestionDifficulty difficulty
) {
}