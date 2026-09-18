package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.QuestionDifficulty;

public record QuestionListResponse(
        Long id,
        Long topicId,
        String slug,
        String question,
        QuestionDifficulty difficulty
) {
}
