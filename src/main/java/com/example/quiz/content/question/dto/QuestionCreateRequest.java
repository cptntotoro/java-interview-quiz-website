package com.example.quiz.content.question.dto;

import com.example.quiz.content.question.QuestionDifficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuestionCreateRequest(

        @NotNull
        Long topicId,

        @NotBlank
        @Size(max = 150)
        String slug,

        @NotBlank
        String question,

        @NotBlank
        String answer,

        String explanation,

        @NotNull
        QuestionDifficulty difficulty
) {
}
