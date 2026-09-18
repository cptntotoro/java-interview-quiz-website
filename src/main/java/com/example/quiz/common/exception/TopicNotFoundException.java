package com.example.quiz.common.exception;

import jakarta.validation.constraints.NotNull;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException(String slug) {
        super("Топик не найден. Слаг: " + slug);
    }

    public TopicNotFoundException(@NotNull Long id) {
        super("Топик не найден. Id: " + id);
    }
}