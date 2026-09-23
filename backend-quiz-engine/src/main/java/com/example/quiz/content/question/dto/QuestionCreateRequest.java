package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.QuestionDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO запроса на создание вопроса
 *
 * @param topicUuid   UUID топика
 * @param slug        слаг
 * @param question    вопрос
 * @param answer      ответ
 * @param explanation объяснение
 * @param difficulty  сложность
 */
public record QuestionCreateRequest(

        @NotNull
        UUID topicUuid,

        @NotBlank
        @Size(max = 150)
        String slug,

        @NotBlank
        String question,

        @NotBlank
        String answer,

        String explanation,

        @NotNull
        QuestionDifficulty difficulty
) {
}
