package com.example.quiz.content.question.query;

/**
 * Модель ответа и объяснения опубликованного вопроса
 *
 * @param referenceAnswer эталонный ответ
 * @param explanation     объяснение
 */
public record PublicQuestionAnswerView(
        String referenceAnswer,
        String explanation
) {
}
