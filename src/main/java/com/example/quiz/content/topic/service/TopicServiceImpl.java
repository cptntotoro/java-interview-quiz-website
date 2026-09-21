package com.example.quiz.content.topic.service;

import com.example.quiz.common.exception.DuplicateSlugException;
import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicUpdateRequest;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class TopicServiceImpl implements TopicService {

    /**
     * JPA репозиторий тем
     */
    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional
    @Override
    public Topic create(TopicCreateRequest request) {

        if (topicRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException(request.slug());
        }

        if (request.parentUuid() != null && !topicRepository.existsById(request.parentUuid())) {
            throw new TopicNotFoundException(request.parentUuid());
        }

        Instant now = Instant.now();

        Topic topic = new Topic();

        topic.setSlug(request.slug());
        topic.setName(request.name());
        topic.setDescription(request.description());
        topic.setParentUuid(request.parentUuid());
        topic.setStatus(ContentStatus.DRAFT);
        topic.setSortOrder(0);
        topic.setCreatedAt(now);
        topic.setUpdatedAt(now);

        return topicRepository.save(topic);
    }

    @Transactional
    @Override
    public Topic update(UUID uuid, TopicUpdateRequest request) {

        Topic topic = topicRepository.findById(uuid)
                .orElseThrow(() -> new TopicNotFoundException(uuid));

        if (request.parentUuid() != null) {

            if (request.parentUuid().equals(uuid)) {
                throw new IllegalArgumentException("Тема не может быть собственным родителем");
            }

            if (!topicRepository.existsById(request.parentUuid())) {
                throw new TopicNotFoundException(request.parentUuid());
            }
        }

        topic.setName(request.name());
        topic.setDescription(request.description());
        topic.setParentUuid(request.parentUuid());

        if (request.sortOrder() != null) {
            topic.setSortOrder(request.sortOrder());
        }

        topic.setUpdatedAt(Instant.now());

        return topicRepository.save(topic);
    }

    @Transactional
    @Override
    public Topic publish(UUID uuid) {

        Topic topic = topicRepository.findById(uuid)
                .orElseThrow(() -> new TopicNotFoundException(uuid));

        Instant now = Instant.now();

        topic.setStatus(ContentStatus.PUBLISHED);
        topic.setPublishedAt(now);
        topic.setUpdatedAt(now);

        return topicRepository.save(topic);
    }

    @Transactional
    @Override
    public Topic archive(UUID uuid) {

        Topic topic = topicRepository.findById(uuid)
                .orElseThrow(() -> new TopicNotFoundException(uuid));

        topic.setStatus(ContentStatus.ARCHIVED);
        topic.setUpdatedAt(Instant.now());

        return topicRepository.save(topic);
    }
}