package com.example.quiz.content.question.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.dto.AdminQuestionBatchCreateRequest;
import com.example.quiz.content.question.dto.AdminQuestionBatchPublishRequest;
import com.example.quiz.content.question.dto.AdminQuestionCreateRequest;
import com.example.quiz.content.question.dto.AdminQuestionUpdateRequest;
import com.example.quiz.content.question.entity.Question;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.query.AdminQuestionDetailsView;
import com.example.quiz.content.question.query.AdminQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;

import java.util.UUID;

/**
 * Административный сервис вопросов
 */
public interface AdminQuestionService {

    /**
     * Получить вопросы с фильтрами
     *
     * @param difficulty фильтр по сложности
     * @param status     фильтр по статусу
     * @param page       номер страницы (0-based)
     * @param size       размер страницы
     * @param sort       параметр сортировки
     * @param direction  направление сортировки
     * @return страница вопросов
     */
    PageResponse<AdminQuestionListView> find(UUID topicUuid, QuestionDifficulty difficulty, ContentStatus status, int page, int size,
                                             QuestionSortField sort, SortDirection direction);

    /**
     * Создать вопрос
     *
     * @param request данные вопроса
     * @return созданный вопрос
     */
    Question create(AdminQuestionCreateRequest request);

    /**
     * Массово создать вопросы
     *
     * @param request данные вопросов
     * @return созданные вопросы
     */
    java.util.List<Question> createBatch(AdminQuestionBatchCreateRequest request);

    /**
     * Обновить вопрос
     *
     * @param uuid    UUID вопроса
     * @param request новые данные
     * @return обновленный вопрос
     */
    Question update(UUID uuid, AdminQuestionUpdateRequest request);

    /**
     * Опубликовать вопрос
     *
     * @param uuid UUID вопроса
     * @return опубликованный вопрос
     */
    Question publish(UUID uuid);

    /**
     * Массово опубликовать вопросы
     *
     * @param request UUID вопросов
     * @return опубликованные вопросы
     */
    java.util.List<Question> publishBatch(AdminQuestionBatchPublishRequest request);

    /**
     * Архивировать вопрос
     *
     * @param uuid UUID вопроса
     * @return архивированный вопрос
     */
    Question archive(UUID uuid);

    /**
     * Получить вопрос по UUID
     *
     * @param uuid UUID вопроса
     * @return вопрос
     */
    AdminQuestionDetailsView findByUuid(UUID uuid);
}
