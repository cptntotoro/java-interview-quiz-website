package com.example.quiz.content.topic.query;

import java.util.UUID;

/**
 * Модель темы из БД для детального просмотра темы
 *
 * @param uuid UUID темы
 * @param slug слаг
 * @param name название
 * @param description описание
 * @param parentUuid UUID родителя
 * @param status статус темы
 * @param sortOrder сортировка
 */
public record TopicDetailsView(
        UUID uuid,
        String slug,
        String name,
        String description,
        UUID parentUuid,
        String status,
        Integer sortOrder
) {
}