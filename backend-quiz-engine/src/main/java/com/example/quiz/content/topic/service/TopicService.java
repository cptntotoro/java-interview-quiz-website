package com.example.quiz.content.topic.service;

import java.util.Set;
import java.util.UUID;

public interface TopicService {

    /**
     * Проверяет существование темы
     *
     * @param uuid UUID темы
     */
    void validateExists(UUID uuid);

    /**
     * Проверяет существование тем
     *
     * @param uuids UUID тем
     */
    void validateExists(Set<UUID> uuids);
}
