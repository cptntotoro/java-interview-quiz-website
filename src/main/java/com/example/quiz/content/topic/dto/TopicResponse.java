package com.example.quiz.content.topic.dto;

public record TopicResponse(
        Long id,
        String slug,
        String name,
        String description,
        Long parentId,
        String status,
        Integer sortOrder
) {
}
