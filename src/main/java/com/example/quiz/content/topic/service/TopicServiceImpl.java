package com.example.quiz.content.topic.service;

import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicResponse;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional
    @Override
    public TopicResponse create(TopicCreateRequest request) {

        if (topicRepository.existsBySlug(request.slug())) {
            throw new TopicNotFoundException(request.slug());
        }

        if (request.parentId() != null
                && !topicRepository.existsById(request.parentId())) {
            throw new TopicNotFoundException(request.parentId());
        }

        Topic topic = new Topic();

        topic.setSlug(request.slug());
        topic.setName(request.name());
        topic.setDescription(request.description());
        topic.setParentId(request.parentId());
        topic.setStatus(ContentStatus.DRAFT);
        topic.setSortOrder(0);

        var now = java.time.Instant.now();

        topic.setCreatedAt(now);
        topic.setUpdatedAt(now);

        Topic saved = topicRepository.save(topic);

        return toResponse(saved);
    }

    @Transactional
    @Override
    public TopicResponse publish(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        Instant now = Instant.now();

        topic.setStatus(ContentStatus.PUBLISHED);
        topic.setPublishedAt(now);
        topic.setUpdatedAt(now);

        return toResponse(topic);
    }

    private TopicResponse toResponse(Topic topic) {
        return new TopicResponse(
                topic.getId(),
                topic.getSlug(),
                topic.getName(),
                topic.getDescription(),
                topic.getParentId(),
                topic.getStatus().name(),
                topic.getSortOrder()
        );
    }
}
