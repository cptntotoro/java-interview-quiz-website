package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.entity.QuestionMatchingPair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA репозиторий пар для сопоставления
 */
public interface QuestionMatchingPairRepository extends JpaRepository<QuestionMatchingPair, UUID> {

    /**
     * Удалить пары вопроса
     *
     * @param questionUuid UUID вопроса
     */
    void deleteAllByQuestionUuid(UUID questionUuid);
}
