package com.example.quiz.content.question.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Вариант ответа
 */
@Getter
@Setter
@Entity
@Table(name = "question_option")
@NoArgsConstructor
public class QuestionOption {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "question_uuid", nullable = false)
    private UUID questionUuid;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean correct;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;
}