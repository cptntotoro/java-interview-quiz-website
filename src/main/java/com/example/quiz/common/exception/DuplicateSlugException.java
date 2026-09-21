package com.example.quiz.common.exception;

public class DuplicateSlugException extends RuntimeException {

    public DuplicateSlugException(String slug) {
        super("Ресурс с таким слагом уже существует: " + slug);
    }
}