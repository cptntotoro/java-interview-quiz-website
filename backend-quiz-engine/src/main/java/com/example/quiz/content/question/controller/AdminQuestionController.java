package com.example.quiz.content.question.controller;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.dto.*;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.mapper.AdminQuestionQueryMapper;
import com.example.quiz.content.question.query.AdminQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import com.example.quiz.content.question.service.AdminQuestionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Админка вопросов
 */
@RestController
@RequestMapping("/api/v1/admin/questions")
public class AdminQuestionController {

    /**
     * Административный сервис вопросов
     */
    private final AdminQuestionService adminQuestionService;

    /**
     * Маппер вопросов для списка в админке
     */
    private final AdminQuestionQueryMapper adminQuestionQueryMapper;

    public AdminQuestionController(AdminQuestionService adminQuestionService,
                                   AdminQuestionQueryMapper adminQuestionQueryMapper) {
        this.adminQuestionService = adminQuestionService;
        this.adminQuestionQueryMapper = adminQuestionQueryMapper;
    }

    @GetMapping
    public PageResponse<AdminQuestionListResponse> find(
            @RequestParam(required = false) QuestionDifficulty difficulty,
            @RequestParam(required = false) ContentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "UUID") QuestionSortField sort,
            @RequestParam(defaultValue = "ASC") SortDirection direction
    ) {
        PageResponse<AdminQuestionListView> result = adminQuestionService.find(difficulty, status, page,
                size, sort, direction);

        return new PageResponse<>(
                result.content().stream()
                        .map(adminQuestionQueryMapper::toListResponse)
                        .toList(),
                result.page(),
                result.size(),
                result.hasNext()
        );
    }

    @PostMapping
    public AdminQuestionResponse create(@Valid @RequestBody AdminQuestionCreateRequest request) {
        Question question = adminQuestionService.create(request);
        return adminQuestionQueryMapper.toResponse(question);
    }

    @PostMapping("/batch")
    public AdminQuestionBatchResponse createBatch(@Valid @RequestBody AdminQuestionBatchCreateRequest request) {
        List<Question> questions = adminQuestionService.createBatch(request);
        List<AdminQuestionResponse> responses = questions.stream().map(adminQuestionQueryMapper::toResponse).toList();
        return new AdminQuestionBatchResponse(responses);
    }

    @PutMapping("/{uuid}")
    public AdminQuestionResponse update(@PathVariable UUID uuid, @Valid @RequestBody AdminQuestionUpdateRequest request) {
        Question question = adminQuestionService.update(uuid, request);
        return adminQuestionQueryMapper.toResponse(question);
    }

    @PatchMapping("/{uuid}/publish")
    public AdminQuestionResponse publish(@PathVariable UUID uuid) {
        Question question = adminQuestionService.publish(uuid);
        return adminQuestionQueryMapper.toResponse(question);
    }

    @PostMapping("/batch/publish")
    public AdminQuestionBatchResponse publishBatch(@Valid @RequestBody AdminQuestionBatchPublishRequest request) {
        List<Question> questions = adminQuestionService.publishBatch(request);
        List<AdminQuestionResponse> responses = questions.stream()
                .map(adminQuestionQueryMapper::toResponse)
                .toList();
        return new AdminQuestionBatchResponse(responses);
    }

    @PatchMapping("/{uuid}/archive")
    public AdminQuestionResponse archive(@PathVariable UUID uuid) {
        Question question = adminQuestionService.archive(uuid);
        return adminQuestionQueryMapper.toResponse(question);
    }
}