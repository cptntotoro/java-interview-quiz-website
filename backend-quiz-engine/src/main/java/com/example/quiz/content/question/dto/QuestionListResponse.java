package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.QuestionDifficulty;
import lombok.Builder;

import java.util.UUID;

/**
 * DTO ответа с предпросмотром вопроса (для списка)
 *
 * @param uuid UUID вопроса
 * @param topicUuid UUID темы
 * @param slug слаг
 * @param question вопрос
 * @param difficulty сложность
 */
@Builder
public record QuestionListResponse(
        UUID uuid,
        UUID topicUuid,
        String slug,
        String question,
        QuestionDifficulty difficulty
) {
}
