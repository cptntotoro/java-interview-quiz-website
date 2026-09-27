package com.example.quiz.content.question.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO варианта ответа
 *
 * @param text    текст варианта
 * @param correct правильный вариант
 */
public record AdminQuestionOptionRequest(

        @NotBlank
        String text,

        boolean correct
) {
}
