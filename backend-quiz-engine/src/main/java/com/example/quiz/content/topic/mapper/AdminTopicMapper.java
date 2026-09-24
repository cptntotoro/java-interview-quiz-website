package com.example.quiz.content.topic.mapper;

import com.example.quiz.content.topic.dto.AdminTopicResponse;
import com.example.quiz.content.topic.entity.Topic;
import org.springframework.stereotype.Component;

/**
 * Маппер тем в DTO для админки
 */
@Component
public class AdminTopicMapper {

    /**
     * Смаппить тему в DTO ответа темы для админки
     *
     * @param topic тема
     * @return DTO ответа темы для админки
     */
    public AdminTopicResponse toResponse(Topic topic) {
        return new AdminTopicResponse(
                topic.getUuid(),
                topic.getSlug(),
                topic.getName(),
                topic.getDescription(),
                topic.getParentUuid(),
                topic.getStatus(),
                topic.getSortOrder()
        );
    }
}