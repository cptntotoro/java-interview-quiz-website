package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.exception.QuestionNotFoundException;
import com.example.quiz.content.question.dto.QuestionDetailsResponse;
import com.example.quiz.content.question.dto.QuestionListResponse;
import com.example.quiz.content.question.repository.QuestionQueryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicQuestionServiceImpl implements PublicQuestionService {

    private static final int DEFAULT_LIMIT = 20;
    private static final int MAX_LIMIT = 100;

    private final QuestionQueryRepository questionQueryRepository;

    public PublicQuestionServiceImpl(QuestionQueryRepository questionQueryRepository) {
        this.questionQueryRepository = questionQueryRepository;
    }

    @Override
    public PageResponse<QuestionListResponse> findPublished(Integer requestedLimit, Long afterId) {
        int limit = normalizeLimit(requestedLimit);

        List<QuestionListResponse> questions = questionQueryRepository.findPublished(afterId, limit + 1);

        boolean hasNext = questions.size() > limit;

        if (hasNext) {
            questions = questions.subList(0, limit);
        }

        Long nextCursor = hasNext ? questions.getLast().id() : null;

        return new PageResponse<>(questions, limit, nextCursor, hasNext);
    }

    @Override
    public QuestionDetailsResponse findPublishedBySlug(String slug) {
        return questionQueryRepository.findPublishedBySlug(slug).orElseThrow(() -> new QuestionNotFoundException(slug));
    }

    private int normalizeLimit(Integer requestedLimit) {
        if (requestedLimit == null) {
            return DEFAULT_LIMIT;
        }

        if (requestedLimit < 1) {
            throw new IllegalArgumentException("limit must be greater than 0");
        }

        return Math.min(requestedLimit, MAX_LIMIT);
    }
}
