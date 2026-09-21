package com.example.quiz.common.exception;

import java.util.UUID;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(String slug) {
        super("Вопрос не найден. Слаг: " + slug);
    }

    public QuestionNotFoundException(UUID uuid) {
        super("Вопрос не найден. UUID: " + uuid);
    }
}