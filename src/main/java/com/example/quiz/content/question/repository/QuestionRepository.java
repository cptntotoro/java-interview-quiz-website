package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA репозиторий вопросов
 */
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    /**
     * Проверить сущестование вопроса по слагу
     *
     * @param slug слаг
     * @return да / нет
     */
    boolean existsBySlug(String slug);
}
