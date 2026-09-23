package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.QuestionDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO запроса на обновление вопроса
 *
 * @param topicUuid UUID темы
 * @param question вопрос
 * @param answer ответ
 * @param explanation объяснение
 * @param difficulty сложность
 */
public record QuestionUpdateRequest(

        @NotNull
        UUID topicUuid,

        @NotBlank
        String question,

        @NotBlank
        String answer,

        String explanation,

        @NotNull
        QuestionDifficulty difficulty
) {
}
