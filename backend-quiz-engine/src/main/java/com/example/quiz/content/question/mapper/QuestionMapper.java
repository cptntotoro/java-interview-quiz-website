package com.example.quiz.content.question.mapper;

import com.example.quiz.content.question.dto.QuestionResponse;
import com.example.quiz.content.question.entity.Question;
import org.springframework.stereotype.Component;

/**
 * Маппер вопросов
 */
@Component
public class QuestionMapper {

    public QuestionResponse toResponse(Question question) {
        return new QuestionResponse(
                question.getUuid(),
                question.getTopicUuid(),
                question.getSlug(),
                question.getQuestion(),
                question.getAnswer(),
                question.getExplanation(),
                question.getDifficulty(),
                question.getStatus()
        );
    }
}