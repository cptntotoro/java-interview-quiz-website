package com.example.quiz.content.question.repository;

import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.question.entity.QuestionDifficulty;
import com.example.quiz.content.question.entity.QuestionType;
import com.example.quiz.content.question.query.AdminQuestionDetailsView;
import com.example.quiz.content.question.query.AdminQuestionListView;
import com.example.quiz.content.question.query.QuestionSortField;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для чтения вопросов в админке
 */
@Repository
public class AdminQuestionQueryRepository {

    private final JdbcClient jdbcClient;

    public AdminQuestionQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Получить страницу вопросов для админки
     *
     * @param difficulty фильтр по сложности
     * @param status     фильтр по статусу
     * @param page       номер страницы (0-based)
     * @param size       размер страницы + 1 запись
     * @param sort       параметр сортировки
     * @param direction  направление сортировки
     * @return список вопросов
     */
    public List<AdminQuestionListView> find(UUID topicUuid, QuestionDifficulty difficulty, ContentStatus status,
                                            int page, int size, QuestionSortField sort, SortDirection direction) {

        int offset = page * size;
        int queryLimit = size + 1;

        String orderBy = sort.sqlExpression() + " " + direction.name();

        if (sort != QuestionSortField.UUID) {
            orderBy += ", q.uuid " + direction.name();
        }

        String topicCondition = topicUuid == null ? "" : " AND q.topic_uuid = :topicUuid";
        String difficultyCondition = difficulty == null ? "" : " AND q.difficulty = :difficulty";
        String statusCondition = status == null ? "" : " AND q.status = :status";

        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    t.slug AS topic_slug,
                    q.slug,
                    q.question,
                    q.question_type,
                    q.difficulty,
                    q.status,
                    q.created_at,
                    q.updated_at
                FROM question q
                JOIN topic t ON t.uuid = q.topic_uuid
                WHERE 1 = 1
                  %s
                  %s
                  %s
                ORDER BY %s
                LIMIT :limit
                OFFSET :offset
                """.formatted(
                topicCondition,
                difficultyCondition,
                statusCondition,
                orderBy
        );

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("limit", queryLimit)
                .param("offset", offset);

        if (topicUuid != null) {
            query = query.param("topicUuid", topicUuid);
        }

        if (difficulty != null) {
            query = query.param("difficulty", difficulty.name());
        }

        if (status != null) {
            query = query.param("status", status.name());
        }

        return query
                .query((rs, rowNum) -> new AdminQuestionListView(
                        rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("topic_slug"),
                        rs.getString("slug"),
                        rs.getString("question"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        QuestionDifficulty.valueOf(rs.getString("difficulty")),
                        ContentStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("created_at").toInstant(),
                        rs.getTimestamp("updated_at").toInstant()
                ))
                .list();
    }

    /**
     * Получить вопрос по UUID
     *
     * @param uuid UUID вопроса
     * @return вопрос
     */
    public Optional<AdminQuestionDetailsView> findByUuid(UUID uuid) {
        return jdbcClient.sql("""
                        SELECT
                            q.uuid,
                            q.topic_uuid,
                            t.slug AS topic_slug,
                            q.slug,
                            q.question,
                            q.answer,
                            q.explanation,
                            q.question_type,
                            q.difficulty,
                            q.status,
                            q.created_at,
                            q.updated_at,
                            q.published_at
                        FROM question q
                        JOIN topic t ON t.uuid = q.topic_uuid
                        WHERE q.uuid = :uuid
                        """)
                .param("uuid", uuid)
                .query((rs, rowNum) -> new AdminQuestionDetailsView(
                        rs.getObject("uuid", UUID.class),
                        rs.getObject("topic_uuid", UUID.class),
                        rs.getString("topic_slug"),
                        rs.getString("slug"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("explanation"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        QuestionDifficulty.valueOf(rs.getString("difficulty")),
                        ContentStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("created_at").toInstant(),
                        rs.getTimestamp("updated_at").toInstant(),
                        rs.getTimestamp("published_at") == null
                                ? null
                                : rs.getTimestamp("published_at").toInstant()
                ))
                .optional();
    }
}
