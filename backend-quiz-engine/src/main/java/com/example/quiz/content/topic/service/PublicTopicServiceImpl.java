package com.example.quiz.content.topic.service;

import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.topic.query.TopicDetailsView;
import com.example.quiz.content.topic.query.TopicListView;
import com.example.quiz.content.topic.repository.PublicTopicQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicTopicServiceImpl implements PublicTopicService {

    /**
     * Репозиторий для чтения тем
     */
    private final PublicTopicQueryRepository publicTopicQueryRepository;

    public PublicTopicServiceImpl(PublicTopicQueryRepository publicTopicQueryRepository) {
        this.publicTopicQueryRepository = publicTopicQueryRepository;
    }

    @Override
    public List<TopicListView> findPublished() {
        return publicTopicQueryRepository.findPublished();
    }

    @Override
    public TopicDetailsView findPublishedBySlug(String slug) {
        return publicTopicQueryRepository.findPublishedBySlug(slug)
                .orElseThrow(() -> new TopicNotFoundException(slug));
    }
}
