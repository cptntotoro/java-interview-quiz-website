package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.entity.QuestionOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA репозиторий элементов последовательности
 */
public interface QuestionOrderItemRepository extends JpaRepository<QuestionOrderItem, UUID> {

    /**
     * Удалить элементы вопроса
     *
     * @param questionUuid UUID вопроса
     */
    void deleteAllByQuestionUuid(UUID questionUuid);
}
