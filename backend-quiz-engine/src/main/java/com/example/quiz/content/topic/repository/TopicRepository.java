package com.example.quiz.content.topic.repository;

import com.example.quiz.content.topic.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * JPA репозиторий тем
 */
public interface TopicRepository extends JpaRepository<Topic, UUID> {

    /**
     * Проверить существование темы по слагу
     *
     * @param slug слаг
     * @return да / нет
     */
    boolean existsBySlug(String slug);
}
