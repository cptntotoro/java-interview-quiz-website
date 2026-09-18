package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.QuestionDifficulty;

public record QuestionDetailsResponse(
        Long id,
        Long topicId,
        String slug,
        String question,
        String answer,
        String explanation,
        QuestionDifficulty difficulty
) {
}
