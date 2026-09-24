package com.example.quiz.content.question.dto;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;

import java.util.UUID;

/**
 * DTO ответа с полным вопросом
 *
 * @param uuid
 * @param topicUuid
 * @param slug        слаг
 * @param question    вопрос
 * @param answer      ответ
 * @param explanation объяснение
 * @param difficulty  сложность
 * @param type        тип
 * @param status      статус
 */
public record AdminQuestionResponse(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionDifficulty difficulty,
        QuestionType type,
        ContentStatus status
) {
}
