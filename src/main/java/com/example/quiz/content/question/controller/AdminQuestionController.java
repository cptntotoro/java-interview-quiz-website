package com.example.quiz.content.question.controller;

import com.example.quiz.content.question.dto.QuestionCreateRequest;
import com.example.quiz.content.question.dto.QuestionResponse;
import com.example.quiz.content.question.dto.QuestionUpdateRequest;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.mapper.QuestionMapper;
import com.example.quiz.content.question.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PatchMapping("/{uuid}/archive")
    public QuestionResponse archive(@PathVariable UUID uuid) {
        Question question = questionService.archive(uuid);
        return questionMapper.toResponse(question);
    }
}