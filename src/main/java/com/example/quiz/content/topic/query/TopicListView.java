package com.example.quiz.content.topic.query;

import java.util.UUID;

/**
 * Модель из БД для списка тем
 *
 * @param uuid UUID темы
 * @param slug слаг
 * @param name название
 * @param description описание
 * @param parentUuid UUID родителя
 * @param status статус темы
 * @param sortOrder сортировка
 */
public record TopicListView(
        UUID uuid,
        String slug,
        String name,
        String description,
        UUID parentUuid,
        String status,
        Integer sortOrder
) {
}