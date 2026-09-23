package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
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

    /**
     * Получить вопросы по списку слагов
     *
     * @param slugs список слагов
     * @return существующие вопросы
     */
    List<Question> findAllBySlugIn(Collection<String> slugs);
}
