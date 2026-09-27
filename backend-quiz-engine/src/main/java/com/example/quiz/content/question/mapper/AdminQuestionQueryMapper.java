package com.example.quiz.content.question.mapper;

import com.example.quiz.content.question.dto.AdminQuestionDetailsResponse;
import com.example.quiz.content.question.dto.AdminQuestionListResponse;
import com.example.quiz.content.question.query.AdminQuestionDetailsView;
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
        return AdminQuestionListResponse.builder()
                .uuid(view.uuid())
                .topicUuid(view.topicUuid())
                .topicSlug(view.topicSlug())
                .slug(view.slug())
                .question(view.question())
                .type(view.type())
                .levelId(view.levelId())
                .status(view.status())
                .createdAt(view.createdAt())
                .updatedAt(view.updatedAt())
                .build();
    }

    /**
     * Преобразовать модель вопроса в DTO детального просмотра
     *
     * @param view модель вопроса
     * @return DTO вопроса
     */
    public AdminQuestionDetailsResponse toDetailsResponse(AdminQuestionDetailsView view) {
        return AdminQuestionDetailsResponse.builder()
                .uuid(view.uuid())
                .topicUuid(view.topicUuid())
                .topicSlug(view.topicSlug())
                .slug(view.slug())
                .question(view.question())
                .referenceAnswer(view.referenceAnswer())
                .explanation(view.explanation())
                .type(view.type())
                .levelId(view.levelId())
                .status(view.status())
                .createdAt(view.createdAt())
                .updatedAt(view.updatedAt())
                .publishedAt(view.publishedAt())
                .build();
    }
}
