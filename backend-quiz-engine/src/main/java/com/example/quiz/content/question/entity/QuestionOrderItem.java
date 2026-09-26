package com.example.quiz.content.question.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Элемент последовательности
 */
@Getter
@Setter
@Entity
@Table(name = "question_order_item")
@NoArgsConstructor
public class QuestionOrderItem {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "question_uuid", nullable = false)
    private UUID questionUuid;

    @Column(nullable = false)
    private String text;

    @Column(name = "correct_order", nullable = false)
    private int correctOrder;
}