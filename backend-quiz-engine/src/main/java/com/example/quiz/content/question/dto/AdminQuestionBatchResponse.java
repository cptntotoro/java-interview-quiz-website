package com.example.quiz.content.question.dto;

import java.util.List;

/**
 * DTO ответа на массовое создание вопросов
 */
// TODO: Добавить наполнение для фронта или удалить и заменить на список?
public record AdminQuestionBatchResponse(
        List<AdminQuestionResponse> questions
) {
}
