package com.example.quiz.content.question.controller;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.content.question.dto.QuestionDetailsResponse;
import com.example.quiz.content.question.dto.QuestionListResponse;
import com.example.quiz.content.question.service.PublicQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/questions")
public class PublicQuestionController {

    private final PublicQuestionService publicQuestionService;

    public PublicQuestionController(PublicQuestionService publicQuestionService) {
        this.publicQuestionService = publicQuestionService;
    }

    @GetMapping
    public PageResponse<QuestionListResponse> findPublished(@RequestParam(required = false) Integer limit,
                                                            @RequestParam(required = false) Long afterId) {
        return publicQuestionService.findPublished(limit, afterId);
    }

    @GetMapping("/{slug}")
    public QuestionDetailsResponse findPublishedBySlug(@PathVariable String slug) {
        return publicQuestionService.findPublishedBySlug(slug);
    }
}