package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Set;
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
     * Получить список дублирующихся слагов
     *
     * @param slugs список слагов
     * @return уже существующие слаги
     */
    @Query("""
            SELECT q.slug
            FROM Question q
            WHERE q.slug IN :slugs
            """)
    Set<String> findExistingSlugs(Collection<String> slugs);
}
