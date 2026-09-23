package com.example.quiz.content.topic.mapper;

import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.entity.Topic;
import org.springframework.stereotype.Component;

/**
 * Маппер тем в DTO
 */
@Component
public class TopicMapper {

    public TopicResponse toResponse(Topic topic) {
        return new TopicResponse(
                topic.getUuid(),
                topic.getSlug(),
                topic.getName(),
                topic.getDescription(),
                topic.getParentUuid(),
                topic.getStatus().name(),
                topic.getSortOrder()
        );
    }
}