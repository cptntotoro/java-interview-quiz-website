package com.example.quiz.content.question.mapper;

import com.example.quiz.content.question.dto.QuestionDetailsResponse;
import com.example.quiz.content.question.dto.QuestionListResponse;
import com.example.quiz.content.question.query.QuestionDetailsView;
import com.example.quiz.content.question.query.QuestionListView;
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
    public QuestionListResponse toListResponse(QuestionListView view) {
        return QuestionListResponse.builder()
                .uuid(view.uuid())
                .topicUuid(view.topicUuid())
                .slug(view.slug())
                .question(view.question())
                .difficulty(view.difficulty())
                .build();
    }

    /**
     * Смаппить ответ с содержанием вопроса в DTO ответа
     *
     * @param view содержание вопроса
     * @return DTO ответа с содержанием вопроса
     */
    public QuestionDetailsResponse toDetailsResponse(QuestionDetailsView view) {
        return QuestionDetailsResponse.builder()
                .uuid(view.uuid())
                .topicUuid(view.topicUuid())
                .slug(view.slug())
                .question(view.question())
                .answer(view.answer())
                .explanation(view.explanation())
                .difficulty(view.difficulty())
                .build();
    }
}