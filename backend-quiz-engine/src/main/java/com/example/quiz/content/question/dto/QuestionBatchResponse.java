package com.example.quiz.content.question.dto;

import java.util.List;

/**
 * Результат массового создания вопросов
 */
public record QuestionBatchResponse(
        List<QuestionResponse> questions
) {
}
