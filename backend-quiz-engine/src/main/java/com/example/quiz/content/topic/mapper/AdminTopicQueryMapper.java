package com.example.quiz.content.topic.mapper;

import com.example.quiz.content.topic.dto.AdminTopicListResponse;
import com.example.quiz.content.topic.query.AdminTopicListView;
import org.springframework.stereotype.Component;

/**
 * Маппер тем из БД в DTO админки
 */
@Component
public class AdminTopicQueryMapper {

    /**
     * Преобразовать модель темы в DTO
     *
     * @param view модель темы
     * @return DTO темы
     */
    public AdminTopicListResponse toListResponse(
            AdminTopicListView view
    ) {
        return new AdminTopicListResponse(
                view.uuid(),
                view.slug(),
                view.name(),
                view.description(),
                view.parentUuid(),
                view.status(),
                view.sortOrder(),
                view.createdAt(),
                view.updatedAt(),
                view.publishedAt()
        );
    }
}
