package com.example.quiz.content.question.repository;

import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.question.entity.QuestionType;
import com.example.quiz.content.question.query.PublicQuestionAnswerView;
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
     * @param levelId   идентификатор уровня
     * @param page      номер страницы (0-based)
     * @param size      размер страницы + 1 запись
     * @param sort      параметр сортировки
     * @param direction направление сортировки
     * @return Запрос на предпросмотр вопрооа (для списка)
     */
    public List<PublicQuestionListView> findPublished(Short levelId, int page, int size,
                                                      QuestionSortField sort, SortDirection direction) {
        int offset = page * size;
        int queryLimit = size + 1;

        String orderBy = sort.sqlExpression() + " " + direction.name();

        if (sort != QuestionSortField.UUID) {
            orderBy += ", q.uuid " + direction.name();
        }

        String levelCondition = levelId == null
                ? ""
                : " AND q.level_id = :levelId";

        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    q.slug,
                    q.question,
                    q.question_type,
                    q.level_id
                FROM question q
                JOIN topic t ON t.uuid = q.topic_uuid
                WHERE q.status = 'PUBLISHED'
                  AND t.status = 'PUBLISHED'
                  %s
                ORDER BY %s
                LIMIT :limit
                OFFSET :offset
                """.formatted(levelCondition, orderBy);

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("limit", queryLimit)
                .param("offset", offset);

        if (levelId != null) {
            query = query.param("levelId", levelId);
        }

        return query
                .query((rs, rowNum) -> new PublicQuestionListView(
                        rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("question"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        rs.getObject("level_id", Short.class)                ))
                .list();
    }

    /**
     * Получить опубликованный вопрос по слагу
     *
     * @param slug слаг вопроса
     * @return детали вопроса
     */
    public Optional<PublicQuestionDetailsView> findPublishedBySlug(String slug) {
        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    q.slug,
                    q.question,
                    q.reference_answer,
                    q.explanation,
                    q.question_type,
                    q.level_id
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
                        rs.getString("reference_answer"),
                        rs.getString("explanation"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        rs.getObject("level_id", Short.class)
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
    public List<PublicQuestionListView> findPublishedByTopic(String topicSlug, Short levelId,
                                                             int page, int size, QuestionSortField sort,
                                                             SortDirection direction) {
        int offset = page * size;
        int queryLimit = size + 1;

        String orderBy = sort.sqlExpression() + " " + direction.name();

        if (sort != QuestionSortField.UUID) {
            orderBy += ", q.uuid " + direction.name();
        }

        String levelCondition = levelId == null
                ? ""
                : " AND q.level_id = :levelId";

        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    q.slug,
                    q.question,
                    q.question_type,
                    q.level_id
                FROM question q
                JOIN topic t ON t.uuid = q.topic_uuid
                WHERE t.slug = :topicSlug
                  AND t.status = 'PUBLISHED'
                  AND q.status = 'PUBLISHED'
                  %s
                ORDER BY %s
                LIMIT :limit
                OFFSET :offset
                """.formatted(levelCondition, orderBy);

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("topicSlug", topicSlug)
                .param("limit", queryLimit)
                .param("offset", offset);

        if (levelId != null) {
            query = query.param("levelId", levelId);
        }

        return query
                .query((rs, rowNum) -> new PublicQuestionListView(
                        rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("question"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        rs.getObject("level_id", Short.class)                ))
                .list();
    }

    public Optional<PublicQuestionAnswerView> findPublishedAnswerBySlug(String slug) {
        String sql = """
                SELECT
                    q.reference_answer,
                    q.explanation
                FROM question q
                WHERE q.slug = :slug
                  AND q.status = 'PUBLISHED'
                """;

        return jdbcClient.sql(sql)
                .param("slug", slug)
                .query((rs, rowNum) -> new PublicQuestionAnswerView(
                        rs.getString("reference_answer"),
                        rs.getString("explanation")
                ))
                .optional();
    }
}