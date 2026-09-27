package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.exception.DuplicateSlugException;
import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.dto.AdminQuestionBatchCreateRequest;
import com.example.quiz.content.question.dto.AdminQuestionBatchPublishRequest;
import com.example.quiz.content.question.dto.AdminQuestionCreateRequest;
import com.example.quiz.content.question.dto.AdminQuestionUpdateRequest;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.query.AdminQuestionDetailsView;
import com.example.quiz.content.question.query.AdminQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import com.example.quiz.content.question.repository.AdminQuestionQueryRepository;
import com.example.quiz.content.question.repository.QuestionRepository;
import com.example.quiz.content.topic.service.TopicService;
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
     * Сервис тем
     */
    private final TopicService topicService;

    /**
     * Репозиторий для чтения вопросов в админке
     */
    private final AdminQuestionQueryRepository questionQueryRepository;

    /**
     * Сервис структуры ответа вопроса
     */
    private final QuestionStructureService questionStructureService;

    /**
     * Валидатор структуры вопроса
     */
    private final QuestionStructureValidator questionStructureValidator;

    public AdminQuestionServiceImpl(QuestionRepository questionRepository, TopicService topicService,
                                    AdminQuestionQueryRepository questionQueryRepository,
                                    QuestionStructureService questionStructureService,
                                    QuestionStructureValidator questionStructureValidator) {
        this.questionRepository = questionRepository;
        this.topicService = topicService;
        this.questionQueryRepository = questionQueryRepository;
        this.questionStructureService = questionStructureService;
        this.questionStructureValidator = questionStructureValidator;
    }

    @Override
    public PageResponse<AdminQuestionListView> find(UUID topicUuid, Short levelId, ContentStatus status, int page,
                                                    int size, QuestionSortField sort, SortDirection direction) {
        validatePage(page);
        int normalizedSize = normalizeSize(size);

        List<AdminQuestionListView> questions = questionQueryRepository.find(topicUuid, levelId, status, page,
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
        topicService.validateExists(request.topicUuid());
        validateSlug(request.slug());
        questionStructureValidator.validate(request);

        Instant now = Instant.now();

        Question question = createQuestion(request, now);

        Question savedQuestion = questionRepository.save(question);
        questionStructureService.save(savedQuestion.getUuid(), request);

        return savedQuestion;
    }

    @Transactional
    @Override
    public List<Question> createBatch(AdminQuestionBatchCreateRequest request) {
        validateBatchSlugs(request.questions());
        validateExistingSlugs(request.questions());
        validateTopics(request.questions());

        request.questions()
                .forEach(questionStructureValidator::validate);

        Instant now = Instant.now();

        List<Question> questions = request.questions().stream()
                .map(questionRequest -> createQuestion(questionRequest, now))
                .toList();

        List<Question> savedQuestions = questionRepository.saveAll(questions);

        for (int i = 0; i < savedQuestions.size(); i++) {
            questionStructureService.save(
                    savedQuestions.get(i).getUuid(),
                    request.questions().get(i)
            );
        }

        return savedQuestions;
    }

    @Transactional
    @Override
    public Question update(UUID uuid, AdminQuestionUpdateRequest request) {
        Question question = findQuestion(uuid);

        topicService.validateExists(request.topicUuid());

        questionStructureValidator.validate(request);

        question.setTopicUuid(request.topicUuid());
        question.setQuestion(request.question());
        question.setReferenceAnswer(request.referenceAnswer());
        question.setExplanation(request.explanation());
        question.setType(request.type());
        question.setLevelId(request.levelId());
        question.setUpdatedAt(Instant.now());

        Question savedQuestion = questionRepository.save(question);

        questionStructureService.replace(uuid, request);

        return savedQuestion;
    }

    @Transactional
    @Override
    public Question publish(UUID uuid) {
        Question question = findQuestion(uuid);

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
        Set<UUID> requestedUuids = validateUniqueUuids(request.uuids());

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
                throw new IllegalStateException(
                        "Только вопросы из черновика могут быть опубликованы: " + question.getUuid()
                );
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
        Question question = findQuestion(uuid);

        if (!question.getStatus().canArchive()) {
            throw new IllegalStateException("Архивированные вопросы не могут быть архивированы");
        }

        question.setStatus(ContentStatus.ARCHIVED);
        question.setUpdatedAt(Instant.now());

        return questionRepository.save(question);
    }

    @Override
    public AdminQuestionDetailsView findByUuid(UUID uuid) {
        return questionQueryRepository.findByUuid(uuid)
                .orElseThrow(() -> new QuestionNotFoundException(uuid));
    }

    private Question createQuestion(AdminQuestionCreateRequest request, Instant now) {
        Question question = new Question();
        question.setTopicUuid(request.topicUuid());
        question.setSlug(request.slug());
        question.setQuestion(request.question());
        question.setReferenceAnswer(request.referenceAnswer());
        question.setExplanation(request.explanation());
        question.setType(request.type());
        question.setLevelId(request.levelId());
        question.setStatus(ContentStatus.DRAFT);
        question.setCreatedAt(now);
        question.setUpdatedAt(now);
        return question;
    }

    private void validateTopics(List<AdminQuestionCreateRequest> requests) {
        Set<UUID> topicUuids = requests.stream()
                .map(AdminQuestionCreateRequest::topicUuid)
                .collect(Collectors.toSet());

        topicService.validateExists(topicUuids);
    }

    private void validateExistingSlugs(List<AdminQuestionCreateRequest> requests) {
        Set<String> slugs = requests.stream()
                .map(AdminQuestionCreateRequest::slug)
                .collect(Collectors.toSet());

        Set<String> existingSlugs = questionRepository.findExistingSlugs(slugs);

        if (!existingSlugs.isEmpty()) {
            throw new DuplicateSlugException(
                    "Вопросы с такими слагами уже существуют: " + existingSlugs
            );
        }
    }

    private void validateBatchSlugs(List<AdminQuestionCreateRequest> requests) {
        Set<String> slugs = requests.stream()
                .map(AdminQuestionCreateRequest::slug)
                .collect(Collectors.toSet());

        if (slugs.size() != requests.size()) {
            throw new DuplicateSlugException("В запросе присутствуют одинаковые слаги");
        }
    }

    private Set<UUID> validateUniqueUuids(List<UUID> uuids) {
        Set<UUID> uniqueUuids = new HashSet<>(uuids);

        if (uniqueUuids.size() != uuids.size()) {
            throw new IllegalArgumentException("В запросе присутствуют одинаковые UUID");
        }

        return uniqueUuids;
    }

    private void validateSlug(String slug) {
        if (questionRepository.existsBySlug(slug)) {
            throw new DuplicateSlugException(slug);
        }
    }
    
    private Question findQuestion(UUID uuid) {
        return questionRepository.findById(uuid)
                .orElseThrow(() -> new QuestionNotFoundException(uuid));
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