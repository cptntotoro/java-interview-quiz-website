package com.example.quiz.content.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO запроса на обновление темы
 *
 * @param name название
 * @param description описание
 * @param parentUuid UUID родителя
 * @param sortOrder порядок сортировки
 */
public record TopicUpdateRequest(

        @NotBlank
        @Size(max = 255)
        String name,

        String description,

        UUID parentUuid,

        Integer sortOrder
) {
}
