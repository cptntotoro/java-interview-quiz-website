package com.example.quiz.content.topic.service;

import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.topic.query.TopicDetailsView;
import com.example.quiz.content.topic.query.TopicListView;
import com.example.quiz.content.topic.repository.TopicQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicTopicServiceImpl implements PublicTopicService {

    /**
     * Репозиторий для чтения тем
     */
    private final TopicQueryRepository topicQueryRepository;

    public PublicTopicServiceImpl(TopicQueryRepository topicQueryRepository) {
        this.topicQueryRepository = topicQueryRepository;
    }

    @Override
    public List<TopicListView> findPublished() {
        return topicQueryRepository.findPublished();
    }

    @Override
    public TopicDetailsView findPublishedBySlug(String slug) {
        return topicQueryRepository.findPublishedBySlug(slug)
                .orElseThrow(() -> new TopicNotFoundException(slug));
    }
}
