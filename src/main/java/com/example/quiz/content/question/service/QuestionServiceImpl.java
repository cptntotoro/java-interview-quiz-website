package com.example.quiz.content.question.service;

import com.example.quiz.common.exception.DuplicateSlugException;
import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.dto.QuestionCreateRequest;
import com.example.quiz.content.question.dto.QuestionUpdateRequest;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.repository.QuestionRepository;
import com.example.quiz.content.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

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
        Question question = questionRepository.findById(uuid)
                .orElseThrow(() -> new QuestionNotFoundException(uuid));

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