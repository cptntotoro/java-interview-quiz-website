package com.example.quiz.content.question.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Пара для вопроса на сопоставление
 */
@Getter
@Setter
@Entity
@Table(name = "question_matching_pair")
@NoArgsConstructor
public class QuestionMatchingPair {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "question_uuid", nullable = false)
    private UUID questionUuid;

    @Column(name = "left_text", nullable = false)
    private String leftText;

    @Column(name = "right_text", nullable = false)
    private String rightText;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;
}