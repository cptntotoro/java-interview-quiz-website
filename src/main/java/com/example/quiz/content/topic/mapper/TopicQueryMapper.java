package com.example.quiz.content.topic.mapper;

import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.query.TopicDetailsView;
import com.example.quiz.content.topic.query.TopicListView;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Маппер тем из БД в DTO ответа
 */
@Component
public class TopicQueryMapper {

    public List<TopicResponse> toTopicResponseList(List<TopicListView> topicListViews) {
        return topicListViews.stream()
                .map(topicListView -> TopicResponse.builder()
                        .uuid(topicListView.uuid())
                        .slug(topicListView.slug())
                        .name(topicListView.name())
                        .description(topicListView.description())
                        .parentUuid(topicListView.parentUuid())
                        .status(topicListView.status())
                        .sortOrder(topicListView.sortOrder())
                        .build()
                ).toList();
    }

    public TopicResponse toTopicResponse(TopicDetailsView topicDetailsView) {
        return TopicResponse.builder()
                .uuid(topicDetailsView.uuid())
                .slug(topicDetailsView.slug())
                .name(topicDetailsView.name())
                .description(topicDetailsView.description())
                .parentUuid(topicDetailsView.parentUuid())
                .status(topicDetailsView.status())
                .sortOrder(topicDetailsView.sortOrder())
                .build();
    }
}
