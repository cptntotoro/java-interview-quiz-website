package com.example.quiz.content.question.service;

import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.dto.QuestionCreateRequest;
import com.example.quiz.content.question.dto.QuestionResponse;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.repository.QuestionRepository;
import com.example.quiz.content.topic.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository, TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    @Override
    public QuestionResponse create(QuestionCreateRequest request) {

        if (!topicRepository.existsById(request.topicId())) {
            throw new TopicNotFoundException(request.topicId());
        }

        if (questionRepository.existsBySlug(request.slug())) {
            throw new QuestionNotFoundException(request.slug());
        }

        Instant now = Instant.now();

        Question question = new Question();
        question.setTopicId(request.topicId());
        question.setSlug(request.slug());
        question.setQuestion(request.question());
        question.setAnswer(request.answer());
        question.setExplanation(request.explanation());
        question.setDifficulty(request.difficulty());
        question.setStatus(ContentStatus.DRAFT);
        question.setCreatedAt(now);
        question.setUpdatedAt(now);

        Question saved = questionRepository.save(question);

        return toResponse(saved);
    }

    @Transactional
    @Override
    public QuestionResponse publish(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException(id));

        Instant now = Instant.now();

        question.setStatus(ContentStatus.PUBLISHED);
        question.setPublishedAt(now);
        question.setUpdatedAt(now);

        return toResponse(question);
    }

    private QuestionResponse toResponse(Question question) {
        return new QuestionResponse(question.getId(), question.getTopicId(), question.getSlug(), question.getQuestion(), question.getAnswer(), question.getExplanation(), question.getDifficulty(), question.getStatus());
    }
}
