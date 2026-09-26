package com.example.quiz.content.question.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Уровень подготовки для вопроса
 */
@Getter
@Setter
@Entity
@Table(name = "question_level")
@NoArgsConstructor
public class QuestionLevel {

    @Id
    @Column(nullable = false, updatable = false)
    private Short id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(nullable = false)
    private boolean active;
}