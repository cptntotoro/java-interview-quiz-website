package com.example.quiz.content.topic.mapper;

import com.example.quiz.content.topic.dto.PublicTopicDetailsResponse;
import com.example.quiz.content.topic.dto.PublicTopicResponse;
import com.example.quiz.content.topic.query.TopicDetailsView;
import com.example.quiz.content.topic.query.TopicListView;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Маппер публичных тем из БД в DTO ответа
 */
@Component
public class PublicTopicQueryMapper {

    /**
     * Смаппить список тем в список DTO тем для публичного списка
     *
     * @param views модели тем
     * @return список DTO тем для публичного списка
     */
    public List<PublicTopicResponse> toListResponse(List<TopicListView> views) {
        return views.stream()
                .map(this::toTopicResponse)
                .toList();
    }

    /**
     * Смаппить модель темы в DTO темы для публичного списка
     *
     * @param view модель темы
     * @return DTO темы для публичного списка
     */
    public PublicTopicResponse toTopicResponse(TopicListView view) {
        return PublicTopicResponse.builder()
                .uuid(view.uuid())
                .slug(view.slug())
                .name(view.name())
                .description(view.description())
                .parentUuid(view.parentUuid())
                .build();
    }

    /**
     * Смаппить модель темы в DTO темы для публичного просмотра
     *
     * @param view модель темы
     * @return DTO темы для публичного просмотра
     */
    public PublicTopicDetailsResponse toDetailsResponse(TopicDetailsView view) {
        return PublicTopicDetailsResponse.builder()
                .uuid(view.uuid())
                .slug(view.slug())
                .name(view.name())
                .description(view.description())
                .parentUuid(view.parentUuid())
                .build();
    }
}
