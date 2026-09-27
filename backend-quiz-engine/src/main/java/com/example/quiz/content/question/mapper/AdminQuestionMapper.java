package com.example.quiz.content.question.mapper;

import com.example.quiz.content.question.dto.AdminQuestionResponse;
import com.example.quiz.content.question.entity.Question;
import org.springframework.stereotype.Component;

/**
 * Маппер сущности вопроса в DTO
 */
@Component
public class AdminQuestionMapper {

    /**
     * Смаппить вопрос в DTO
     *
     * @param question вопрос
     * @return DTO вопроса
     */
    public AdminQuestionResponse toResponse(Question question) {
        return AdminQuestionResponse.builder()
                .uuid(question.getUuid())
                .topicUuid(question.getTopicUuid())
                .slug(question.getSlug())
                .question(question.getQuestion())
                .referenceAnswer(question.getReferenceAnswer())
                .explanation(question.getExplanation())
                .type(question.getType())
                .levelId(question.getLevelId())
                .status(question.getStatus())
                .createdAt(question.getCreatedAt())
                .updatedAt(question.getUpdatedAt())
                .build();
    }
}