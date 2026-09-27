package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.entity.QuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

/**
 * DTO запроса на создание вопроса
 *
 * @param topicUuid       UUID топика
 * @param slug            слаг
 * @param question        вопрос
 * @param referenceAnswer ответ
 * @param explanation     объяснение
 * @param type            тип
 * @param levelId         идентификатор уровня
 */
public record AdminQuestionCreateRequest(

        @NotNull
        UUID topicUuid,

        @NotBlank
        @Size(max = 150)
        String slug,

        @NotBlank
        String question,

        String referenceAnswer,

        String explanation,

        @NotNull
        QuestionType type,

        @NotNull
        Short levelId,

        List<@Valid AdminQuestionOptionRequest> options,

        List<@Valid AdminQuestionMatchingPairRequest> matchingPairs,

        List<@Valid AdminQuestionOrderItemRequest> orderItems
) {
}
