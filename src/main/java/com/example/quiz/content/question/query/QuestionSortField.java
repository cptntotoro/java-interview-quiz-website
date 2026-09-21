package com.example.quiz.content.question.query;

/**
 * Поля сортировки вопросов
 */
public enum QuestionSortField {

    UUID("q.uuid"),
    SLUG("q.slug"),
    DIFFICULTY("q.difficulty");

    private final String sqlExpression;

    QuestionSortField(String sqlExpression) {
        this.sqlExpression = sqlExpression;
    }

    public String sqlExpression() {
        return sqlExpression;
    }
}