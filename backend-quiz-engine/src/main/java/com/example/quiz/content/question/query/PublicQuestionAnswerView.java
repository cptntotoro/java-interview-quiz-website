package com.example.quiz.content.question.query;

/**
 * Модель ответа и объяснения опубликованного вопроса
 *
 * @param answer      эталонный ответ
 * @param explanation объяснение
 */
public record PublicQuestionAnswerView(
        String answer,
        String explanation
) {
}
