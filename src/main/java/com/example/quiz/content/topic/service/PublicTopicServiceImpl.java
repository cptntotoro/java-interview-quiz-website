package com.example.quiz.content.topic.service;

import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.repository.TopicQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicTopicServiceImpl implements PublicTopicService {

    private final TopicQueryRepository topicQueryRepository;

    public PublicTopicServiceImpl(TopicQueryRepository topicQueryRepository) {
        this.topicQueryRepository = topicQueryRepository;
    }

    @Override
    public List<TopicResponse> findPublished() {
        return topicQueryRepository.findPublished();
    }

    @Override
    public TopicResponse findPublishedBySlug(String slug) {
        return topicQueryRepository.findPublishedBySlug(slug)
                .orElseThrow(() -> new TopicNotFoundException(slug));
    }
}
