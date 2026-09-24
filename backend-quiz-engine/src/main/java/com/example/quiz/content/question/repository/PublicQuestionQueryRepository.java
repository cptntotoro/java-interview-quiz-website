package com.example.quiz.content.question.repository;

import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;
import com.example.quiz.content.question.query.PublicQuestionDetailsView;
import com.example.quiz.content.question.query.PublicQuestionListView;
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
public class PublicQuestionQueryRepository {

    private final JdbcClient jdbcClient;

    public PublicQuestionQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Получить страницу опубликованных вопросов
     *
     * @param difficulty фильтр по сложности
     * @param page       номер страницы (0-based)
     * @param size       размер страницы + 1 запись
     * @param sort       параметр сортировки
     * @param direction  направление сортировки
     * @return Запрос на предпросмотр вопрооа (для списка)
     */
    public List<PublicQuestionListView> findPublished(QuestionDifficulty difficulty, int page, int size,
                                                      QuestionSortField sort, SortDirection direction) {
        int offset = page * size;
        int queryLimit = size + 1;

        String orderBy = sort.sqlExpression() + " " + direction.name();

        if (sort != QuestionSortField.UUID) {
            orderBy += ", q.uuid " + direction.name();
        }

        String difficultyCondition = difficulty == null
                ? ""
                : " AND q.difficulty = :difficulty";

        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    q.slug,
                    q.question,
                    q.question_type,
                    q.difficulty
                FROM question q
                JOIN topic t ON t.uuid = q.topic_uuid
                WHERE q.status = 'PUBLISHED'
                  AND t.status = 'PUBLISHED'
                  %s
                ORDER BY %s
                LIMIT :limit
                OFFSET :offset
                """.formatted(difficultyCondition, orderBy);

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("limit", queryLimit)
                .param("offset", offset);

        if (difficulty != null) {
            query = query.param("difficulty", difficulty.name());
        }

        return query
                .query((rs, rowNum) -> new PublicQuestionListView(
                        rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("question"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        QuestionDifficulty.valueOf(rs.getString("difficulty"))
                ))
                .list();
    }

    /**
     * Получить опубликованный вопрос по слагу
     *
     * @param slug       слаг вопроса
     * @return детали вопроса
     */
    public Optional<PublicQuestionDetailsView> findPublishedBySlug(String slug) {
        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    q.slug,
                    q.question,
                    q.answer,
                    q.explanation,
                    q.question_type,
                    q.difficulty
                FROM question q
                JOIN topic t ON t.uuid = q.topic_uuid
                WHERE q.slug = :slug
                  AND q.status = 'PUBLISHED'
                  AND t.status = 'PUBLISHED'
                """;

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("slug", slug);

        return query
                .query((rs, rowNum) -> new PublicQuestionDetailsView(
                        rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("explanation"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        QuestionDifficulty.valueOf(rs.getString("difficulty"))
                ))
                .optional();
    }

    /**
     * Получить опубликованные вопросы темы
     *
     * @param topicSlug слаг темы
     * @param page      номер страницы
     * @param size      размер страницы
     * @param sort      сортировка
     * @param direction направление сортировки
     * @return список вопросов
     */
    public List<PublicQuestionListView> findPublishedByTopic(String topicSlug, QuestionDifficulty difficulty,
                                                             int page, int size, QuestionSortField sort,
                                                             SortDirection direction) {
        int offset = page * size;
        int queryLimit = size + 1;

        String orderBy = sort.sqlExpression() + " " + direction.name();

        if (sort != QuestionSortField.UUID) {
            orderBy += ", q.uuid " + direction.name();
        }

        String difficultyCondition = difficulty == null
                ? ""
                : " AND q.difficulty = :difficulty";

        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    q.slug,
                    q.question,
                    q.question_type,
                    q.difficulty
                FROM question q
                JOIN topic t ON t.uuid = q.topic_uuid
                WHERE t.slug = :topicSlug
                  AND t.status = 'PUBLISHED'
                  AND q.status = 'PUBLISHED'
                  %s
                ORDER BY %s
                LIMIT :limit
                OFFSET :offset
                """.formatted(difficultyCondition, orderBy);

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("topicSlug", topicSlug)
                .param("limit", queryLimit)
                .param("offset", offset);

        if (difficulty != null) {
            query = query.param("difficulty", difficulty.name());
        }

        return query
                .query((rs, rowNum) -> new PublicQuestionListView(
                        rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("question"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        QuestionDifficulty.valueOf(rs.getString("difficulty"))
                ))
                .list();
    }
}