package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA репозиторий вариантов ответа
 */
public interface QuestionOptionRepository extends JpaRepository<QuestionOption, UUID> {

    /**
     * Удалить варианты ответа вопроса
     *
     * @param questionUuid UUID вопроса
     */
    void deleteAllByQuestionUuid(UUID questionUuid);
}
