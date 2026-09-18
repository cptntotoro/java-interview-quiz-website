package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.content.question.dto.QuestionDetailsResponse;
import com.example.quiz.content.question.dto.QuestionListResponse;

public interface PublicQuestionService {
    PageResponse<QuestionListResponse> findPublished(Integer requestedLimit, Long afterId);

    QuestionDetailsResponse findPublishedBySlug(String slug);
}
