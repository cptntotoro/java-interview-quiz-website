package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.query.PublicQuestionAnswerView;
import com.example.quiz.content.question.query.PublicQuestionDetailsView;
import com.example.quiz.content.question.query.PublicQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;

/**
 * Публичный сервис вопросов
 */
public interface PublicQuestionService {

    /**
     * Получить опубликованные вопросы по сложности с пагинацией
     *
     * @param levelId   идентификатор уровня
     * @param page      номер страницы (0-based)
     * @param size      размер страницы
     * @param sort      сортировка
     * @param direction направление сортировки
     * @return страница вопросов
     */
    PageResponse<PublicQuestionListView> findPublishedByDifficulty(Short levelId, int page, int size,
                                                                   QuestionSortField sort, SortDirection direction);

    /**
     * Получить опубликованный вопрос по слагу
     *
     * @param slug слаг вопроса
     * @return детали вопроса
     */
    PublicQuestionDetailsView findPublishedBySlug(String slug);

    /**
     * Получить опубликованные вопросы темы
     *
     * @param topicSlug слаг темы
     * @param levelId   идентификатор уровня
     * @param page      номер страницы
     * @param size      размер страницы
     * @param sort      сортировка
     * @param direction направление сортировки
     * @return страница вопросов
     */
    PageResponse<PublicQuestionListView> findPublishedByTopicAndDifficulty(String topicSlug, Short levelId,
                                                                           int page, int size, QuestionSortField sort,
                                                                           SortDirection direction);

    /**
     * Получить отвеь и объяснение вопроса по слагу
     *
     * @param slug слаг вопроса
     * @return Модель ответа и объяснения опубликованного вопроса
     */
    PublicQuestionAnswerView findPublishedAnswerBySlug(String slug);
}