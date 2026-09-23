package com.example.quiz.content.question.controller;

import com.example.quiz.content.question.dto.*;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.mapper.QuestionMapper;
import com.example.quiz.content.question.service.QuestionService;
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
    private final QuestionService questionService;

    /**
     * Маппер вопросов
     */
    private final QuestionMapper questionMapper;

    public AdminQuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @PostMapping
    public QuestionResponse create(@Valid @RequestBody QuestionCreateRequest request) {
        Question question = questionService.create(request);
        return questionMapper.toResponse(question);
    }

    @PostMapping("/batch")
    public QuestionBatchResponse createBatch(@Valid @RequestBody QuestionBatchCreateRequest request) {
        List<Question> questions = questionService.createBatch(request);
        List<QuestionResponse> responses = questions.stream().map(questionMapper::toResponse).toList();
        return new QuestionBatchResponse(responses);
    }

    @PutMapping("/{uuid}")
    public QuestionResponse update(@PathVariable UUID uuid, @Valid @RequestBody QuestionUpdateRequest request) {
        Question question = questionService.update(uuid, request);
        return questionMapper.toResponse(question);
    }

    @PatchMapping("/{uuid}/publish")
    public QuestionResponse publish(@PathVariable UUID uuid) {
        Question question = questionService.publish(uuid);
        return questionMapper.toResponse(question);
    }

    @PostMapping("/batch/publish")
    public QuestionBatchResponse publishBatch(@Valid @RequestBody QuestionBatchPublishRequest request) {
        List<Question> questions = questionService.publishBatch(request);
        List<QuestionResponse> responses = questions.stream().map(questionMapper::toResponse).toList();
        return new QuestionBatchResponse(responses);
    }

    @PatchMapping("/{uuid}/archive")
    public QuestionResponse archive(@PathVariable UUID uuid) {
        Question question = questionService.archive(uuid);
        return questionMapper.toResponse(question);
    }
}