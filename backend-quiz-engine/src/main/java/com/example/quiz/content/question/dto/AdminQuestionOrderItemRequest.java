package com.example.quiz.content.question.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO элемента последовательности
 *
 * @param text элемент последовательности
 */
public record AdminQuestionOrderItemRequest(

        @NotBlank
        String text
) {
}
