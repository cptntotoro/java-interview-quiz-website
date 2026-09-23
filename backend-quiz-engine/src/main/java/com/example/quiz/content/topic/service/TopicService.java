package com.example.quiz.content.topic.service;

import com.example.quiz.content.topic.dto.TopicCreateRequest;
import com.example.quiz.content.topic.dto.TopicUpdateRequest;
import com.example.quiz.content.topic.entity.Topic;

import java.util.UUID;

/**
 * Сервис управления темами
 */
public interface TopicService {

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