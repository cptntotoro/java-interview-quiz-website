package com.example.quiz.content.question.mapper;

import com.example.quiz.content.question.dto.AdminQuestionListResponse;
import com.example.quiz.content.question.dto.AdminQuestionResponse;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.query.AdminQuestionListView;
import org.springframework.stereotype.Component;

/**
 * Маппер вопросов для списка в админке
 */
@Component
public class AdminQuestionQueryMapper {

    /**
     * Смаппить модель вопроса в DTO
     *
     * @param view модель вопроса
     * @return DTO ответа со списком вопросов в админке
     */
    public AdminQuestionListResponse toListResponse(AdminQuestionListView view) {
        return new AdminQuestionListResponse(
                view.uuid(),
                view.topicUuid(),
                view.topicSlug(),
                view.slug(),
                view.question(),
                view.type(),
                view.difficulty(),
                view.status(),
                view.createdAt(),
                view.updatedAt()
        );
    }

    /**
     * Смаппить модель вопроса
     *
     * @param question вопрос
     * @return DTO ответа с полным вопросом
     */
    public AdminQuestionResponse toResponse(Question question) {
        return new AdminQuestionResponse(
                question.getUuid(),
                question.getTopicUuid(),
                question.getSlug(),
                question.getQuestion(),
                question.getAnswer(),
                question.getExplanation(),
                question.getDifficulty(),
                question.getType(),
                question.getStatus()
        );
    }
}
