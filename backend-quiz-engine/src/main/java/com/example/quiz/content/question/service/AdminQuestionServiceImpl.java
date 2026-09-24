package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.exception.DuplicateSlugException;
import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.common.exception.TopicNotFoundException;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.dto.AdminQuestionBatchCreateRequest;
import com.example.quiz.content.question.dto.AdminQuestionBatchPublishRequest;
import com.example.quiz.content.question.dto.AdminQuestionCreateRequest;
import com.example.quiz.content.question.dto.AdminQuestionUpdateRequest;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.query.AdminQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import com.example.quiz.content.question.repository.AdminQuestionQueryRepository;
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
public class AdminQuestionServiceImpl implements AdminQuestionService {

    /**
     * Максимальный размер страницы
     */
    private static final int MAX_SIZE = 100;

    /**
     * JPA репозиторий вопросов
     */
    private final QuestionRepository questionRepository;

    /**
     * Репозиторий тем
     */
    private final TopicRepository topicRepository;

    /**
     * Репозиторий для чтения вопросов в админке
     */
    private final AdminQuestionQueryRepository questionQueryRepository;

    public AdminQuestionServiceImpl(QuestionRepository questionRepository, TopicRepository topicRepository,
                                    AdminQuestionQueryRepository questionQueryRepository) {
        this.questionRepository = questionRepository;
        this.topicRepository = topicRepository;
        this.questionQueryRepository = questionQueryRepository;
    }

    @Override
    public PageResponse<AdminQuestionListView> find(QuestionDifficulty difficulty, ContentStatus status, int page,
                                                    int size, QuestionSortField sort, SortDirection direction) {
        validatePage(page);
        int normalizedSize = normalizeSize(size);

        List<AdminQuestionListView> questions = questionQueryRepository.find(difficulty, status, page,
                normalizedSize, sort, direction);

        boolean hasNext = questions.size() > normalizedSize;

        if (hasNext) {
            questions = questions.subList(0, normalizedSize);
        }

        return new PageResponse<>(questions, page, normalizedSize, hasNext);
    }

    @Transactional
    @Override
    public Question create(AdminQuestionCreateRequest request) {
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
        question.setType(request.type());
        question.setDifficulty(request.difficulty());
        question.setStatus(ContentStatus.DRAFT);
        question.setCreatedAt(now);
        question.setUpdatedAt(now);

        return questionRepository.save(question);
    }

    @Transactional
    @Override
    public List<Question> createBatch(AdminQuestionBatchCreateRequest request) {
        Set<String> slugs = request.questions().stream()
                .map(AdminQuestionCreateRequest::slug)
                .collect(Collectors.toSet());

        if (slugs.size() != request.questions().size()) {
            throw new DuplicateSlugException("В запросе присутствуют одинаковые слаги");
        }

        Set<String> existingSlugs = questionRepository.findExistingSlugs(slugs);

        if (!existingSlugs.isEmpty()) {
            throw new DuplicateSlugException("Вопросы с такими слагами уже существуют: " + existingSlugs);
        }

        Set<UUID> topicUuids = request.questions().stream()
                .map(AdminQuestionCreateRequest::topicUuid)
                .collect(Collectors.toSet());

        Set<UUID> existingTopicUuids = topicRepository.findAllById(topicUuids).stream()
                .map(Topic::getUuid)
                .collect(Collectors.toSet());

        if (existingTopicUuids.size() != topicUuids.size()) {
            Set<UUID> missingTopicUuids = new HashSet<>(topicUuids);
            missingTopicUuids.removeAll(existingTopicUuids);

            throw new TopicNotFoundException(String.valueOf(missingTopicUuids));
        }

        Instant now = Instant.now();

        List<Question> questions = request.questions().stream()
                .map(questionRequest -> {
                    Question question = new Question();

                    question.setUuid(UUID.randomUUID());
                    question.setTopicUuid(questionRequest.topicUuid());
                    question.setSlug(questionRequest.slug());
                    question.setQuestion(questionRequest.question());
                    question.setAnswer(questionRequest.answer());
                    question.setExplanation(questionRequest.explanation());
                    question.setType(questionRequest.type());
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
    public Question update(UUID uuid, AdminQuestionUpdateRequest request) {
        Question question = questionRepository.findById(uuid)
                .orElseThrow(() -> new QuestionNotFoundException(uuid));

        if (!topicRepository.existsById(request.topicUuid())) {
            throw new TopicNotFoundException(request.topicUuid());
        }

        question.setTopicUuid(request.topicUuid());
        question.setQuestion(request.question());
        question.setAnswer(request.answer());
        question.setExplanation(request.explanation());
        question.setType(request.type());
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
    public List<Question> publishBatch(AdminQuestionBatchPublishRequest request) {
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
        Question question = questionRepository.findById(uuid)
                .orElseThrow(() -> new QuestionNotFoundException(uuid));

        if (!question.getStatus().canArchive()) {
            throw new IllegalStateException("Архивированные вопросы не могут быть архивированы");
        }

        question.setStatus(ContentStatus.ARCHIVED);
        question.setUpdatedAt(Instant.now());

        return questionRepository.save(question);
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