package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.QuestionDifficulty;
import lombok.Builder;

import java.util.UUID;

/**
 * DTO ответа с содержанием вопроса
 *
 * @param uuid UUID вопроса
 * @param topicUuid UUID темы
 * @param slug слаг
 * @param question вопрос
 * @param answer ответ
 * @param explanation объяснение
 * @param difficulty сложность
 */
@Builder
// TODO: Мб убрать uuid, он избыточен
public record QuestionDetailsResponse(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionDifficulty difficulty
) {
}
