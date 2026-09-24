package com.example.quiz.content.topic.dto;

import com.example.quiz.content.common.ContentStatus;
import lombok.Builder;

import java.util.UUID;

/**
 * DTO ответа темы для админки
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
public record AdminTopicResponse(
        UUID uuid,
        String slug,
        String name,
        String description,
        UUID parentUuid,
        ContentStatus status,
        Integer sortOrder
) {
}
