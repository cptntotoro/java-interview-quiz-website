package com.example.quiz.content.question.controller;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.dto.AdminQuestionResponse;
import com.example.quiz.content.question.dto.PublicQuestionListResponse;
import com.example.quiz.content.question.dto.QuestionAnswerResponse;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.mapper.QuestionQueryMapper;
import com.example.quiz.content.question.query.PublicQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import com.example.quiz.content.question.service.PublicQuestionService;
import org.springframework.web.bind.annotation.*;

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
    public PageResponse<PublicQuestionListResponse> findPublished(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "20") int size,
                                                                  @RequestParam(defaultValue = "UUID") QuestionSortField sort,
                                                                  @RequestParam(defaultValue = "ASC") SortDirection direction,
                                                                  @RequestParam(required = false) QuestionDifficulty difficulty) {
        PageResponse<PublicQuestionListView> result = publicQuestionService.findPublishedByDifficulty(difficulty, page, size, sort, direction);
        return new PageResponse<>(result.content().stream().map(questionQueryMapper::toListResponse).toList(), result.page(), result.size(), result.hasNext());
    }

    @GetMapping("/{slug}")
    public AdminQuestionResponse findPublishedBySlug(@PathVariable String slug) {
        return questionQueryMapper.toQuestionResponse(publicQuestionService.findPublishedBySlug(slug));
    }

    @GetMapping("/topics/{topicSlug}/questions")
    public PageResponse<PublicQuestionListResponse> findPublishedByTopic(@PathVariable String topicSlug,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "20") int size,
                                                                         @RequestParam(defaultValue = "UUID") QuestionSortField sort,
                                                                         @RequestParam(defaultValue = "ASC") SortDirection direction,
                                                                         @RequestParam(required = false) QuestionDifficulty difficulty) {
        PageResponse<PublicQuestionListView> result = publicQuestionService.findPublishedByTopicAndDifficulty(topicSlug,
                difficulty, page, size, sort, direction);
        return new PageResponse<>(result.content().stream()
                .map(questionQueryMapper::toListResponse)
                .toList(), result.page(), result.size(), result.hasNext());
    }

    @GetMapping("/{slug}/answer")
    public QuestionAnswerResponse getAnswer(@PathVariable String slug) {
        return questionQueryMapper.toAnswerResponse(publicQuestionService.findPublishedAnswerBySlug(slug));
    }
}