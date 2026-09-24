package com.example.quiz.content.topic.dto;

import lombok.Builder;

import java.util.UUID;

/**
 * DTO темы для публичного просмотра
 *
 * @param uuid       UUID темы
 * @param slug       слаг
 * @param name       название
 * @param description описание
 * @param parentUuid UUID родительской темы
 */
@Builder
public record PublicTopicDetailsResponse(
        UUID uuid,
        String slug,
        String name,
        String description,
        UUID parentUuid
) {
}
