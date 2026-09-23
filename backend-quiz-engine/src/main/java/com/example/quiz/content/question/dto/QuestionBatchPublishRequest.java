package com.example.quiz.content.question.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

/**
 * DTO запроса на массовую публикацию вопросов
 */
public record QuestionBatchPublishRequest(

        @NotEmpty
        @Size(max = 100)
        List<UUID> uuids
) {
}