package com.example.quiz.content.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * DTO запроса на создание темы
 *
 * @param slug слаг
 * @param name название
 * @param description описание
 * @param parentUuid UUID родителя
 */
public record TopicCreateRequest(

        @NotBlank
        @Size(max = 150)
        String slug,

        @NotBlank
        @Size(max = 255)
        String name,

        String description,

        UUID parentUuid
) {
}