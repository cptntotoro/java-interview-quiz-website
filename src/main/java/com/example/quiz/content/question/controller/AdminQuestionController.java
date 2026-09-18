package com.example.quiz.content.question.controller;

import com.example.quiz.content.question.dto.QuestionCreateRequest;
import com.example.quiz.content.question.dto.QuestionResponse;
import com.example.quiz.content.question.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/questions")
public class AdminQuestionController {

    private final QuestionService questionService;

    public AdminQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse create(@Valid @RequestBody QuestionCreateRequest request) {
        return questionService.create(request);
    }

    @PatchMapping("/{id}/publish")
    public QuestionResponse publish(@PathVariable Long id) {
        return questionService.publish(id);
    }
}