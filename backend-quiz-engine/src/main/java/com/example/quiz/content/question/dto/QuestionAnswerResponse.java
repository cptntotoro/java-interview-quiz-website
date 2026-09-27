package com.example.quiz.content.question.dto;

import lombok.Builder;

/**
 * Ответ на вопрос
 *
 * @param referenceAnswer эталонный ответ
 * @param explanation     объяснение
 */
@Builder
public record QuestionAnswerResponse(
        String referenceAnswer,
        String explanation
) {
}
