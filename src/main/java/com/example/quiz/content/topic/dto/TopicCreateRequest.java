package com.example.quiz.content.topic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TopicCreateRequest(

        @NotBlank
        @Size(max = 150)
        String slug,

        @NotBlank
        @Size(max = 255)
        String name,

        String description,

        Long parentId
) {
}