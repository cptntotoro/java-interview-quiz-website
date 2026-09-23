package com.example.quiz.content.topic.dto;

import lombok.Builder;

import java.util.UUID;

/**
 * DTO ответа темы
 *
 * @param uuid UUID темы
 * @param slug слаг
 * @param name название
 * @param description описание
 * @param parentUuid UUID родителя
 * @param status статус
 * @param sortOrder порядок сортировки
 */
@Builder
public record TopicResponse(
        UUID uuid,
        String slug,
        String name,
        String description,
        UUID parentUuid,
        String status,
        Integer sortOrder
) {
}
