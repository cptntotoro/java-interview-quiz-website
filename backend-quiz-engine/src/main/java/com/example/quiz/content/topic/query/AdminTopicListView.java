package com.example.quiz.content.topic.query;

import com.example.quiz.content.common.ContentStatus;

import java.time.Instant;
import java.util.UUID;

/**
 * Модель темы из БД для списка в админке
 *
 * @param uuid        UUID темы
 * @param slug        слаг
 * @param name        название
 * @param description описание
 * @param parentUuid  UUID родителя
 * @param status      статус
 * @param sortOrder   порядок сортировки
 * @param createdAt   время создания
 * @param updatedAt   время обновления
 * @param publishedAt время публикации
 */
public record AdminTopicListView(
        UUID uuid,
        String slug,
        String name,
        String description,
        UUID parentUuid,
        ContentStatus status,
        Integer sortOrder,
        Instant createdAt,
        Instant updatedAt,
        Instant publishedAt
) {
}
