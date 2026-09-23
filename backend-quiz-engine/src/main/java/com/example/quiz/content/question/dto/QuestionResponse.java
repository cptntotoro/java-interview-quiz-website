package com.example.quiz.content.question.dto;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.QuestionDifficulty;

import java.util.UUID;

/**
 * DTO ответа с полным вопросом
 *
 * @param uuid
 * @param topicUuid
 * @param slug слаг
 * @param question вопрос
 * @param answer ответ
 * @param explanation объяснение
 * @param difficulty сложность
 * @param status статус
 */
public record QuestionResponse(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionDifficulty difficulty,
        ContentStatus status
) {
}
