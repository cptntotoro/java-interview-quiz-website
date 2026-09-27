package com.example.quiz.content.question.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO пары для сопоставления
 *
 * @param leftText  левая часть
 * @param rightText правая часть
 */
public record AdminQuestionMatchingPairRequest(

        @NotBlank
        String leftText,

        @NotBlank
        String rightText
) {
}
