package com.example.quiz.content.topic.service;

import com.example.quiz.common.dto.PageResponse;
import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicUpdateRequest;
import com.example.quiz.content.topic.entity.Topic;
import com.example.quiz.content.topic.query.AdminTopicListView;

import java.util.UUID;

/**
 * Сервис управления темами
 */
public interface AdminTopicService {

    /**
     * Получить темы с фильтром по статусу
     *
     * @param status    фильтр по статусу
     * @param page      номер страницы (0-based)
     * @param size      размер страницы
     * @param direction направление сортировки
     * @return страница тем
     */
    PageResponse<AdminTopicListView> find(ContentStatus status, int page, int size, SortDirection direction);

    /**
     * Создать тему
     *
     * @param request DTO запроса на создание темы
     * @return тема
     */
    Topic create(TopicCreateRequest request);

    /**
     * Обновить тему
     *
     * @param uuid UUID темы
     * @param request DTO запроса на обновление темы
     * @return тема
     */
    Topic update(UUID uuid, TopicUpdateRequest request);

    /**
     * Опубликовать тему
     *
     * @param uuid UUID темы
     * @return тема
     */
    Topic publish(UUID uuid);

    /**
     * Архивировать тему
     *
     * @param uuid UUID темы
     * @return тема
     */
    Topic archive(UUID uuid);
}