package com.example.quiz.content.topic.repository;

import com.example.quiz.content.topic.dto.TopicResponse;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TopicQueryRepository {

    private final JdbcClient jdbcClient;

    public TopicQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<TopicResponse> findPublished() {
        return jdbcClient.sql("""
                SELECT
                    id,
                    slug,
                    name,
                    description,
                    parent_id,
                    status,
                    sort_order
                FROM topic
                WHERE status = 'PUBLISHED'
                ORDER BY sort_order, id
                """)
                .query((rs, rowNum) -> new TopicResponse(
                        rs.getLong("id"),
                        rs.getString("slug"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getObject("parent_id", Long.class),
                        rs.getString("status"),
                        rs.getInt("sort_order")
                ))
                .list();
    }

    public Optional<TopicResponse> findPublishedBySlug(String slug) {
        return jdbcClient.sql("""
                SELECT
                    id,
                    slug,
                    name,
                    description,
                    parent_id,
                    status,
                    sort_order
                FROM topic
                WHERE slug = :slug
                  AND status = 'PUBLISHED'
                """)
                .param("slug", slug)
                .query((rs, rowNum) -> new TopicResponse(
                        rs.getLong("id"),
                        rs.getString("slug"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getObject("parent_id", Long.class),
                        rs.getString("status"),
                        rs.getInt("sort_order")
                ))
                .optional();
    }
}
