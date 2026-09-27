package com.example.quiz.content.question.repository;

import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
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
     * @param levelId   дентификатор уровня
     * @param status    фильтр по статусу
     * @param page      номер страницы (0-based)
     * @param size      размер страницы + 1 запись
     * @param sort      параметр сортировки
     * @param direction направление сортировки
     * @return список вопросов
     */
    public List<AdminQuestionListView> find(UUID topicUuid, Short levelId, ContentStatus status,
                                            int page, int size, QuestionSortField sort, SortDirection direction) {

        int offset = page * size;
        int queryLimit = size + 1;

        String orderBy = sort.sqlExpression() + " " + direction.name();

        if (sort != QuestionSortField.UUID) {
            orderBy += ", q.uuid " + direction.name();
        }

        String topicCondition = topicUuid == null ? "" : " AND q.topic_uuid = :topicUuid";
        String levelCondition = levelId == null ? "" : " AND q.level_id = :levelId";
        String statusCondition = status == null ? "" : " AND q.status = :status";

        String sql = """
                SELECT
                    q.uuid,
                    q.topic_uuid,
                    t.slug AS topic_slug,
                    q.slug,
                    q.question,
                    q.question_type,
                    q.level_id,
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
                levelCondition,
                statusCondition,
                orderBy
        );

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("limit", queryLimit)
                .param("offset", offset);

        if (topicUuid != null) {
            query = query.param("topicUuid", topicUuid);
        }

        if (levelId != null) {
            query = query.param("levelId", levelId);
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
                        rs.getObject("level_id", Short.class),
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
                            q.reference_answer,
                            q.explanation,
                            q.question_type,
                            q.level_id,
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
                        rs.getString("reference_answer"),
                        rs.getString("explanation"),
                        QuestionType.valueOf(rs.getString("question_type")),
                        rs.getObject("level_id", Short.class),                        ContentStatus.valueOf(rs.getString("status")),
                        rs.getTimestamp("created_at").toInstant(),
                        rs.getTimestamp("updated_at").toInstant(),
                        rs.getTimestamp("published_at") == null ? null : rs.getTimestamp("published_at").toInstant()
                ))
                .optional();
    }
}
