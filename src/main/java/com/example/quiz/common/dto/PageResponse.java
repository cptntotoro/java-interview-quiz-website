package com.example.quiz.common.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        Integer limit,
        Long nextCursor,
        boolean hasNext
) {
}
