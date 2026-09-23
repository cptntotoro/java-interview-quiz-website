package com.example.quiz.content.topic.service;

import com.example.quiz.content.topic.query.TopicDetailsView;
import com.example.quiz.content.topic.query.TopicListView;

import java.util.List;

/**
 * Публичный сервис тем
 */
public interface PublicTopicService {

    /**
     * Получить опубликованные темы
     *
     * @return список моделей из БД для списка тем
     */
    List<TopicListView> findPublished();

    /**
     * Найти опубликованную тему по слагу
     *
     * @param slug слаг
     * @return DTO ответа темы
     */
    TopicDetailsView findPublishedBySlug(String slug);
}
