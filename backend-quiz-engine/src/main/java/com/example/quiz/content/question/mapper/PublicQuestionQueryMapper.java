package com.example.quiz.content.question.mapper;

import com.example.quiz.content.question.dto.AdminQuestionResponse;
import com.example.quiz.content.question.dto.PublicQuestionListResponse;
import com.example.quiz.content.question.dto.QuestionAnswerResponse;
import com.example.quiz.content.question.query.PublicQuestionAnswerView;
import com.example.quiz.content.question.query.PublicQuestionDetailsView;
import com.example.quiz.content.question.query.PublicQuestionListView;
import org.springframework.stereotype.Component;

/**
 * Маппер вопросов из БД в DTO ответа
 */
@Component
public class PublicQuestionQueryMapper {

    /**
     * Смаппить предпросмотр вопроса (для списка) в DTO ответа
     *
     * @param view запрос на предпросмотр вопрооа (для списка)
     * @return DTO ответа с предпросмотром вопроса (для списка)
     */
    public PublicQuestionListResponse toListResponse(PublicQuestionListView view) {
        return PublicQuestionListResponse.builder()
                .uuid(view.uuid())
                .topicUuid(view.topicUuid())
                .slug(view.slug())
                .question(view.question())
                .type(view.type())
                .levelId(view.levelId())
                .build();
    }

    /**
     * Смаппить ответ с содержанием вопроса в DTO ответа
     *
     * @param view модель публилчного содержания вопроса
     * @return DTO ответа с содержанием вопроса
     */
    public AdminQuestionResponse toQuestionResponse(PublicQuestionDetailsView view) {
        return AdminQuestionResponse.builder()
                .uuid(view.uuid())
                .topicUuid(view.topicUuid())
                .slug(view.slug())
                .question(view.question())
                .referenceAnswer(view.referenceAnswer())
                .explanation(view.explanation())
                .type(view.type())
                .levelId(view.levelId())
                .build();
    }

    /**
     * Смаппить ответ в DTO ответа
     *
     * @param view модель ответа и объяснения опубликованного вопроса
     * @return Ответ на вопрос
     */
    public QuestionAnswerResponse toAnswerResponse(PublicQuestionAnswerView view) {
        return QuestionAnswerResponse.builder()
                .referenceAnswer(view.referenceAnswer())
                .explanation(view.explanation())
                .build();
    }
}