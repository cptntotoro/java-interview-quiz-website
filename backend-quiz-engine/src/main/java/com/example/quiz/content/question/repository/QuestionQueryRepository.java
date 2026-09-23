package com.example.quiz.content.question.repository;

import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.QuestionDifficulty;
import com.example.quiz.content.question.query.QuestionDetailsView;
import com.example.quiz.content.question.query.QuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для чтения опубликованных вопросов
 */
@Repository
public class QuestionQueryRepository {

    private final JdbcClient jdbcClient;

    public QuestionQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Получить страницу опубликованных вопросов
     *
     * @param page      номер страницы (0-based)
     * @param size      размер страницы + 1 запись
     * @param sort      параметр сортировки
     * @param direction направление сортировки
     * @return Запрос на предпросмотр вопрооа (для списка)
     */
    public List<QuestionListView> findPublished(int page, int size, QuestionSortField sort, SortDirection direction) {
        int offset = page * size;
        int queryLimit = size + 1;

        String orderBy = sort.sqlExpression() + " " + direction.name() + ", q.uuid " + direction.name();

        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    q.slug,
                    q.question,
                    q.difficulty
                FROM question q
                JOIN topic t ON t.uuid = q.topic_uuid
                WHERE q.status = 'PUBLISHED'
                  AND t.status = 'PUBLISHED'
                ORDER BY %s
                LIMIT :limit
                OFFSET :offset
                """.formatted(orderBy);

        return jdbcClient.sql(sql)
                .param("limit", queryLimit)
                .param("offset", offset)
                .query((rs, rowNum) -> new QuestionListView(rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("question"),
                        QuestionDifficulty.valueOf(rs.getString("difficulty")))
                ).list();
    }

    /**
     * Получить опубликованный вопрос по слагу
     *
     * @param slug слаг вопроса
     * @return Ответ с содержанием вопроса
     */
    public Optional<QuestionDetailsView> findPublishedBySlug(String slug) {
        return jdbcClient.sql("""
                        SELECT
                            q.uuid,
                            q.topic_uuid,
                            q.slug,
                            q.question,
                            q.answer,
                            q.explanation,
                            q.difficulty
                        FROM question q
                        JOIN topic t ON t.uuid = q.topic_uuid
                        WHERE q.slug = :slug
                          AND q.status = 'PUBLISHED'
                          AND t.status = 'PUBLISHED'
                        """)
                .param("slug", slug)
                .query((rs, rowNum) -> new QuestionDetailsView(rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("question"), rs.getString("answer"),
                        rs.getString("explanation"),
                        QuestionDifficulty.valueOf(rs.getString("difficulty")))
                ).optional();
    }
}