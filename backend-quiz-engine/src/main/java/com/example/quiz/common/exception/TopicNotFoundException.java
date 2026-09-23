package com.example.quiz.common.exception;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException(String slug) {
        super("Топик не найден. Слаг: " + slug);
    }

    public TopicNotFoundException(@NotNull UUID uuid) {
        super("Топик не найден. UUID: " + uuid);
    }
}