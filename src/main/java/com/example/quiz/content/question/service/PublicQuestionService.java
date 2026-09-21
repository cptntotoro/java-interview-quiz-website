package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.query.QuestionDetailsView;
import com.example.quiz.content.question.query.QuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;

/**
 * Публичный сервис вопросов
 */
public interface PublicQuestionService {

    /**
     * Получить опубликованные вопросы с пагинацией
     *
     * @param page      номер страницы (0-based)
     * @param size      размер страницы
     * @param sort      сортировка
     * @param direction направление сортировки
     * @return страница вопросов
     */
    PageResponse<QuestionListView> findPublished(int page, int size, QuestionSortField sort, SortDirection direction);

    /**
     * Получить опубликованные вопросы по слагу
     *
     * @param slug слаг вопроса
     * @return детали вопроса
     */
    QuestionDetailsView findPublishedBySlug(String slug);
}