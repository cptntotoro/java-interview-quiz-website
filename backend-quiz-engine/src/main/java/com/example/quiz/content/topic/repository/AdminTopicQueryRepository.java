package com.example.quiz.content.topic.repository;

import com.example.quiz.common.query.SortDirection;
import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.topic.query.AdminTopicListView;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для чтения тем в админке
 */
@Repository
public class AdminTopicQueryRepository {

    private final JdbcClient jdbcClient;

    public AdminTopicQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Получить страницу тем для админки
     *
     * @param status    фильтр по статусу
     * @param page      номер страницы (0-based)
     * @param size      размер страницы + 1 запись
     * @param direction направление сортировки
     * @return список тем
     */
    public List<AdminTopicListView> find(
            ContentStatus status,
            int page,
            int size,
            SortDirection direction
    ) {
        int offset = page * size;
        int queryLimit = size + 1;

        String sortDirection = direction.name();

        String statusCondition = status == null
                ? ""
                : " AND status = :status";

        String sql = """
                SELECT
                    uuid,
                    slug,
                    name,
                    description,
                    parent_uuid,
                    status,
                    sort_order,
                    created_at,
                    updated_at,
                    published_at
                FROM topic
                WHERE 1 = 1
                  %s
                ORDER BY sort_order %s, uuid %s
                LIMIT :limit
                OFFSET :offset
                """.formatted(statusCondition, sortDirection, sortDirection);

        JdbcClient.StatementSpec query = jdbcClient.sql(sql)
                .param("limit", queryLimit)
                .param("offset", offset);

        if (status != null) {
            query = query.param("status", status.name());
        }

        return query
                .query((rs, rowNum) -> new AdminTopicListView(
                        rs.getObject("uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getObject("parent_uuid", UUID.class),
                        ContentStatus.valueOf(rs.getString("status")),
                        rs.getInt("sort_order"),
                        rs.getTimestamp("created_at").toInstant(),
                        rs.getTimestamp("updated_at").toInstant(),
                        rs.getTimestamp("published_at") == null
                                ? null
                                : rs.getTimestamp("published_at").toInstant()
                ))
                .list();
    }
}