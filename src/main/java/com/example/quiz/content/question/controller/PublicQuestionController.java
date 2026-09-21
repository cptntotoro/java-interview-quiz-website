package com.example.quiz.content.question.controller;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.dto.QuestionDetailsResponse;
import com.example.quiz.content.question.dto.QuestionListResponse;
import com.example.quiz.content.question.mapper.QuestionQueryMapper;
import com.example.quiz.content.question.query.QuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import com.example.quiz.content.question.service.PublicQuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Публичный API вопросов
 */
@RestController
@RequestMapping("/api/v1/public/questions")
public class PublicQuestionController {

    /**
     * Публичный сервис вопросов
     */
    private final PublicQuestionService publicQuestionService;

    /**
     * Маппер вопросов из БД в DTO ответа
     */
    private final QuestionQueryMapper questionQueryMapper;

    public PublicQuestionController(PublicQuestionService publicQuestionService, QuestionQueryMapper questionQueryMapper) {
        this.publicQuestionService = publicQuestionService;
        this.questionQueryMapper = questionQueryMapper;
    }

    @GetMapping
    public PageResponse<QuestionListResponse> findPublished(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size,
                                                            @RequestParam(defaultValue = "ID") QuestionSortField sort,
                                                            @RequestParam(defaultValue = "ASC") SortDirection direction) {
        PageResponse<QuestionListView> result = publicQuestionService.findPublished(page, size, sort, direction);
        return new PageResponse<>(result.content().stream()
                .map(questionQueryMapper::toListResponse)
                .toList(),
                result.page(), result.size(), result.hasNext());
    }

    @GetMapping("/{slug}")
    public QuestionDetailsResponse findPublishedBySlug(@PathVariable String slug) {
        return questionQueryMapper.toDetailsResponse(publicQuestionService.findPublishedBySlug(slug));
    }
}