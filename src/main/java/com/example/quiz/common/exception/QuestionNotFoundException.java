package com.example.quiz.common.exception;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(String slug) {
        super("Вопрос не найден. Слаг: " + slug);
    }

    public QuestionNotFoundException(Long id) {
        super("Вопрос не найден. Id: " + id);
    }
}