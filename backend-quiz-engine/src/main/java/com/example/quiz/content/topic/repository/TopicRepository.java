package com.example.quiz.content.topic.repository;

import com.example.quiz.content.topic.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * JPA репозиторий тем
 */
public interface TopicRepository extends JpaRepository<Topic, UUID> {

    /**
     * Найти тему по слагу
     *
     * @param slug слаг
     * @return тема
     */
    Optional<Topic> findBySlug(String slug);

    /**
     * Проверить существование темы по слагу
     *
     * @param slug слаг
     * @return да / нет
     */
    boolean existsBySlug(String slug);
}
