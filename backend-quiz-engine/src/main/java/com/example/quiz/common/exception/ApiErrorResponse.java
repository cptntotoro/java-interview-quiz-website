package com.example.quiz.common.exception;

import java.time.Instant;

/**
 * Ошибка API
 *
 * @param timestamp время
 * @param status статус
 * @param error ошибка
 * @param message сообщение
 */
public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message
) {
}