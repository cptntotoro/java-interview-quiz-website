package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.entity.QuestionType;
import lombok.Builder;

import java.util.UUID;

/**
 * DTO ответа с предпросмотром вопроса (для списка)
 *
 * @param uuid      UUID вопроса
 * @param topicUuid UUID темы
 * @param slug      слаг
 * @param question  вопрос
 * @param type      тип
 * @param levelId   идентификатор уровня
 */
@Builder
public record PublicQuestionListResponse(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        QuestionType type,
        Short levelId
) {
}
