package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;
import lombok.Builder;

import java.util.UUID;

/**
 * DTO ответа с содержанием вопроса
 *
 * @param uuid        UUID вопроса
 * @param topicUuid   UUID темы
 * @param slug        слаг
 * @param question    вопрос
 * @param answer      ответ
 * @param explanation объяснение
 * @param type        тип
 * @param difficulty  сложность
 */
@Builder
// TODO: Мб убрать uuid, он избыточен
public record PublicQuestionDetailsResponse(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionType type,
        QuestionDifficulty difficulty
) {
}
