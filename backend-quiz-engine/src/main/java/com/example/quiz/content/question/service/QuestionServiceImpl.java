package com.example.quiz.content.question.service;

import com.example.quiz.common.exception.DuplicateSlugException;
import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.dto.QuestionBatchCreateRequest;
import com.example.quiz.content.question.dto.QuestionBatchPublishRequest;
import com.example.quiz.content.question.dto.QuestionCreateRequest;
import com.example.quiz.content.question.dto.QuestionUpdateRequest;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.repository.QuestionRepository;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    /**
     * JPA репозиторий вопросов
     */
    private final QuestionRepository questionRepository;

    /**
     * JPA репозиторий тем
     */
    private final TopicRepository topicRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    @Override
    public Question create(QuestionCreateRequest request) {
        if (!topicRepository.existsById(request.topicUuid())) {
            throw new TopicNotFoundException(request.topicUuid());
        }

        if (questionRepository.existsBySlug(request.slug())) {
            throw new DuplicateSlugException(request.slug());
        }

        Instant now = Instant.now();

        Question question = new Question();
        question.setTopicUuid(request.topicUuid());
        question.setSlug(request.slug());
        question.setQuestion(request.question());
        question.setAnswer(request.answer());
        question.setExplanation(request.explanation());
        question.setDifficulty(request.difficulty());
        question.setStatus(ContentStatus.DRAFT);
        question.setCreatedAt(now);
        question.setUpdatedAt(now);

        return questionRepository.save(question);
    }

    @Transactional
    @Override
    public List<Question> createBatch(QuestionBatchCreateRequest request) {

        Set<String> slugs = request.questions().stream().map(QuestionCreateRequest::slug).collect(Collectors.toSet());

        if (slugs.size() != request.questions().size()) {
            throw new DuplicateSlugException("В запросе присутствуют одинаковые слаги");
        }

        Set<String> existingSlugs = questionRepository.findAllBySlugIn(slugs).stream().map(Question::getSlug).collect(Collectors.toSet());

        if (!existingSlugs.isEmpty()) {
            throw new DuplicateSlugException("Вопросы с такими слагами уже существуют: " + existingSlugs);
        }

        Set<UUID> topicUuids = request.questions().stream().map(QuestionCreateRequest::topicUuid).collect(Collectors.toSet());

        Set<UUID> existingTopicUuids = topicRepository.findAllById(topicUuids).stream().map(Topic::getUuid).collect(Collectors.toSet());

        if (existingTopicUuids.size() != topicUuids.size()) {
            Set<UUID> missingTopicUuids = new HashSet<>(topicUuids);
            missingTopicUuids.removeAll(existingTopicUuids);

            throw new TopicNotFoundException(String.valueOf(missingTopicUuids));
        }

        Instant now = Instant.now();

        List<Question> questions = request.questions().stream().map(questionRequest -> {
            Question question = new Question();

            question.setUuid(UUID.randomUUID());
            question.setTopicUuid(questionRequest.topicUuid());
            question.setSlug(questionRequest.slug());
            question.setQuestion(questionRequest.question());
            question.setAnswer(questionRequest.answer());
            question.setExplanation(questionRequest.explanation());
            question.setDifficulty(questionRequest.difficulty());
            question.setStatus(ContentStatus.DRAFT);
            question.setCreatedAt(now);
            question.setUpdatedAt(now);

            return question;
        }).toList();

        return questionRepository.saveAll(questions);
    }

    @Transactional
    @Override
    public Question update(UUID uuid, QuestionUpdateRequest request) {
        Question question = questionRepository.findById(uuid).orElseThrow(() -> new QuestionNotFoundException(uuid));

        if (!topicRepository.existsById(request.topicUuid())) {
            throw new TopicNotFoundException(request.topicUuid());
        }

        question.setTopicUuid(request.topicUuid());
        question.setQuestion(request.question());
        question.setAnswer(request.answer());
        question.setExplanation(request.explanation());
        question.setDifficulty(request.difficulty());
        question.setUpdatedAt(Instant.now());

        return questionRepository.save(question);
    }

    @Transactional
    @Override
    public Question publish(UUID uuid) {
        Question question = questionRepository.findById(uuid).orElseThrow(() -> new QuestionNotFoundException(uuid));

        if (!question.getStatus().canPublish()) {
            throw new IllegalStateException("Только вопросы из черновика могут быть опубликованы");
        }

        Instant now = Instant.now();

        question.setStatus(ContentStatus.PUBLISHED);
        question.setPublishedAt(now);
        question.setUpdatedAt(now);

        return questionRepository.save(question);
    }

    @Transactional
    @Override
    public List<Question> publishBatch(QuestionBatchPublishRequest request) {

        Set<UUID> requestedUuids = new HashSet<>(request.uuids());

        if (requestedUuids.size() != request.uuids().size()) {
            throw new IllegalArgumentException("В запросе присутствуют одинаковые UUID");
        }

        List<Question> questions = questionRepository.findAllById(requestedUuids);

        if (questions.size() != requestedUuids.size()) {
            Set<UUID> existingUuids = questions.stream()
                    .map(Question::getUuid)
                    .collect(Collectors.toSet());

            Set<UUID> missingUuids = new HashSet<>(requestedUuids);
            missingUuids.removeAll(existingUuids);

            throw new QuestionNotFoundException(String.valueOf(missingUuids));
        }

        Instant now = Instant.now();

        for (Question question : questions) {

            if (!question.getStatus().canPublish()) {
                throw new IllegalStateException("Только вопросы из черновика могут быть опубликованы: " + question.getUuid());
            }

            question.setStatus(ContentStatus.PUBLISHED);
            question.setPublishedAt(now);
            question.setUpdatedAt(now);
        }

        return questionRepository.saveAll(questions);
    }

    @Transactional
    @Override
    public Question archive(UUID uuid) {
        Question question = questionRepository.findById(uuid).orElseThrow(() -> new QuestionNotFoundException(uuid));

        if (!question.getStatus().canArchive()) {
            throw new IllegalStateException("Архивированные вопросы не могут быть архивированы");
        }

        question.setStatus(ContentStatus.ARCHIVED);
        question.setUpdatedAt(Instant.now());

        return questionRepository.save(question);
    }
}