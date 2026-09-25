package com.example.quiz.content.question.dto;

import lombok.Builder;

/**
 * Ответ на вопрос
 *
 * @param answer      эталонный ответ
 * @param explanation объяснение
 */
@Builder
public record QuestionAnswerResponse(
        String answer,
        String explanation
) {
}
