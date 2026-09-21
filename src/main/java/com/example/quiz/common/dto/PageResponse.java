package com.example.quiz.common.dto;

import java.util.List;

/**
 * Страница
 *
 * @param content контент
 * @param page номер страницы (0-based )
 * @param size размер страницы
 * @param hasNext флаг наличия следующей страницы
 * @param <T> тип контента
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        boolean hasNext
) {
}