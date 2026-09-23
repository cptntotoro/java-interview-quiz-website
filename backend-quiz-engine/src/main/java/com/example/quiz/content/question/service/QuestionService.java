package com.example.quiz.content.question.service;

import com.example.quiz.content.question.dto.QuestionBatchCreateRequest;
import com.example.quiz.content.question.dto.QuestionBatchPublishRequest;
import com.example.quiz.content.question.dto.QuestionCreateRequest;
import com.example.quiz.content.question.dto.QuestionUpdateRequest;
import com.example.quiz.content.question.entity.Question;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Административный сервис вопросов
 */
public interface QuestionService {

    /**
     * Создать вопрос
     *
     * @param request DTO запроса на создание вопроса
     * @return Вопрос
     */
    Question create(QuestionCreateRequest request);

    /**
     * Cоздать список вопросов
     *
     * @param request DTO запроса на создание списка вопросов
     * @return Список вопросов
     */
    List<Question> createBatch(QuestionBatchCreateRequest request);

    /**
     * Опубликовать вопрос
     *
     * @param uuid UUID вопроса
     * @return Вопрос
     */
    Question publish(UUID uuid);

    /**
     * Обновить вопрос
     *
     * @param uuid UUID вопроса
     * @param request DTO запроса на обновление вопроса
     * @return Вопрос
     */
    Question update(UUID uuid, QuestionUpdateRequest request);

    @Transactional
    List<Question> publishBatch(QuestionBatchPublishRequest request);

    /**
     * Архивировать вопрос
     *
     * @param uuid UUID вопроса
     * @return Вопрос
     */
    Question archive(UUID uuid);
}
