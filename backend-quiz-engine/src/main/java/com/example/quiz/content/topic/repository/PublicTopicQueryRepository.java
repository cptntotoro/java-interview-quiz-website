package com.example.quiz.content.topic.repository;

import com.example.quiz.content.topic.query.TopicDetailsView;
import com.example.quiz.content.topic.query.TopicListView;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для публичного чтения тем
 */
@Repository
public class PublicTopicQueryRepository {

    private final JdbcClient jdbcClient;

    public PublicTopicQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    /**
     * Получить список опубликованных тем
     *
     * @return список моделей из БД для списка тем
     */
    public List<TopicListView> findPublished() {
        return jdbcClient.sql("""
                        SELECT
                            uuid,
                            slug,
                            name,
                            description,
                            parent_uuid,
                            status,
                            sort_order
                        FROM topic
                        WHERE status = 'PUBLISHED'
                        ORDER BY sort_order, uuid
                        """)
                .query((rs, rowNum) -> new TopicListView(
                        rs.getObject("uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getObject("parent_uuid", UUID.class),
                        rs.getString("status"),
                        rs.getInt("sort_order")
                ))
                .list();
    }

    /**
     * Получить опубликованную тему по слагу
     *
     * @param slug слаг
     * @return модель темы из БД для детального просмотра темы
     */
    public Optional<TopicDetailsView> findPublishedBySlug(String slug) {
        return jdbcClient.sql("""
                        SELECT
                            uuid,
                            slug,
                            name,
                            description,
                            parent_uuid,
                            status,
                            sort_order
                        FROM topic
                        WHERE slug = :slug
                          AND status = 'PUBLISHED'
                        """)
                .param("slug", slug)
                .query((rs, rowNum) -> new TopicDetailsView(
                        rs.getObject("uuid", UUID.class),
                        rs.getString("slug"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getObject("parent_uuid", UUID.class),
                        rs.getString("status"),
                        rs.getInt("sort_order")
                ))
                .optional();
    }
}