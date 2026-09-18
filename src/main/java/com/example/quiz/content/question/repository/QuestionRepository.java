package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    boolean existsBySlug(String slug);
}
