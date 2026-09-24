package com.example.quiz.content.question.mapper;

import com.example.quiz.content.question.dto.PublicQuestionDetailsResponse;
import com.example.quiz.content.question.dto.PublicQuestionListResponse;
import com.example.quiz.content.question.query.PublicQuestionDetailsView;
import com.example.quiz.content.question.query.PublicQuestionListView;
import org.springframework.stereotype.Component;

/**
 * Маппер вопросов из БД в DTO ответа
 */
@Component
public class QuestionQueryMapper {

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
                .difficulty(view.difficulty())
                .build();
    }

    /**
     * Смаппить ответ с содержанием вопроса в DTO ответа
     *
     * @param view содержание вопроса
     * @return DTO ответа с содержанием вопроса
     */
    public PublicQuestionDetailsResponse toDetailsResponse(PublicQuestionDetailsView view) {
        return PublicQuestionDetailsResponse.builder()
                .uuid(view.uuid())
                .topicUuid(view.topicUuid())
                .slug(view.slug())
                .question(view.question())
                .answer(view.answer())
                .explanation(view.explanation())
                .type(view.type())
                .difficulty(view.difficulty())
                .build();
    }
}