package com.example.quiz.common.exception;

/**
 * Исключение при попытке создать ресурс с существующим слагом
 */
public class DuplicateSlugException extends RuntimeException {

    public DuplicateSlugException(String slug) {
        super("Ресурс с таким слагом уже существует: " + slug);
    }
}