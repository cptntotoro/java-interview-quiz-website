package com.example.quiz.content.question.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * DTO запроса на создание списка вопросов
 */
public record AdminQuestionBatchCreateRequest(

        @NotEmpty
        @Size(max = 100)
        List<@Valid AdminQuestionCreateRequest> questions
) {
}
