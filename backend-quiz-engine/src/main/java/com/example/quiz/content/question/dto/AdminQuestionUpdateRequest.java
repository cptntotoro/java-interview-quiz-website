package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * DTO запроса на обновление вопроса
 *
 * @param topicUuid   UUID темы
 * @param question    вопрос
 * @param answer      ответ
 * @param explanation объяснение
 * @param type        тип
 * @param difficulty  сложность
 */
public record AdminQuestionUpdateRequest(

        @NotNull
        UUID topicUuid,

        @NotBlank
        String question,

        @NotBlank
        String answer,

        String explanation,

        @NotNull
        QuestionType type,

        @NotNull
        QuestionDifficulty difficulty
) {
}
