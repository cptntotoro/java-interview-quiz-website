package com.example.quiz.content.topic.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.exception.DuplicateSlugException;
import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicUpdateRequest;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.query.AdminTopicListView;
import com.example.quiz.content.topic.repository.AdminTopicQueryRepository;
import com.example.quiz.content.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class AdminTopicServiceImpl implements AdminTopicService {

    /**
     * Максимальный размер страницы
     */
    private static final int MAX_SIZE = 100;

    /**
     * JPA репозиторий тем
     */
    private final TopicRepository topicRepository;

    /**
     * Репозиторий для чтения тем в админке
     */
    private final AdminTopicQueryRepository topicQueryRepository;

    public AdminTopicServiceImpl(TopicRepository topicRepository, AdminTopicQueryRepository topicQueryRepository) {
        this.topicRepository = topicRepository;
        this.topicQueryRepository = topicQueryRepository;
    }

    @Override
    public PageResponse<AdminTopicListView> find(ContentStatus status, int page, int size, SortDirection direction) {
        validatePage(page);
        int normalizedSize = normalizeSize(size);

        List<AdminTopicListView> topics = topicQueryRepository.find(
                status,
                page,
                normalizedSize,
                direction
        );

        boolean hasNext = topics.size() > normalizedSize;

        if (hasNext) {
            topics = topics.subList(0, normalizedSize);
        }

        return new PageResponse<>(
                topics,
                page,
                normalizedSize,
                hasNext
        );
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

        if (!topic.getStatus().canPublish()) {
            throw new IllegalStateException("Только темы из черновика могут быть опубликованы");
        }

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

        if (!topic.getStatus().canArchive()) {
            throw new IllegalStateException("Архивированные темы не могут быть архивированы");
        }

        topic.setStatus(ContentStatus.ARCHIVED);
        topic.setUpdatedAt(Instant.now());

        return topicRepository.save(topic);
    }

    private void validatePage(int page) {
        if (page < 0) {
            throw new IllegalArgumentException("номер страницы должен быть больше или равен 0");
        }
    }

    private int normalizeSize(int size) {
        if (size < 1) {
            throw new IllegalArgumentException("размер страницы должен быть больше 0");
        }

        return Math.min(size, MAX_SIZE);
    }
}