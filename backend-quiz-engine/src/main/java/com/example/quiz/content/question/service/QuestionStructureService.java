package com.example.quiz.content.question.service;

import com.example.quiz.content.question.dto.AdminQuestionCreateRequest;
import com.example.quiz.content.question.dto.AdminQuestionUpdateRequest;

import java.util.UUID;

public interface QuestionStructureService {
    void save(UUID questionUuid, AdminQuestionCreateRequest request);

    void save(UUID questionUuid, AdminQuestionUpdateRequest request);

    void replace(UUID questionUuid, AdminQuestionUpdateRequest request);

    void delete(UUID questionUuid);
}
