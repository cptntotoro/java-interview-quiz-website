package com.example.quiz.content.question.service;

import com.example.quiz.content.question.dto.QuestionCreateRequest;
import com.example.quiz.content.question.dto.QuestionResponse;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionService {
    @Transactional
    QuestionResponse create(QuestionCreateRequest request);

    @Transactional
    QuestionResponse publish(Long id);
}
