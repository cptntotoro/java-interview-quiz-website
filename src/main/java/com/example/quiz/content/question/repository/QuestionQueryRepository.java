package com.example.quiz.content.question.repository;

import com.example.quiz.content.question.QuestionDifficulty;
import com.example.quiz.content.question.dto.QuestionDetailsResponse;
import com.example.quiz.content.question.dto.QuestionListResponse;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuestionQueryRepository {

    private final JdbcClient jdbcClient;

    public QuestionQueryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<QuestionListResponse> findPublished(
            Long afterId,
            int limit
    ) {
        String sql = """
            SELECT
                id,
                topic_id,
                slug,
                question,
                difficulty
            FROM question
            WHERE status = 'PUBLISHED'
              AND (:afterId IS NULL OR id > :afterId)
            ORDER BY id
            LIMIT :limit
            """;

        return jdbcClient.sql(sql)
                .param("afterId", afterId)
                .param("limit", limit)
                .query((rs, rowNum) -> new QuestionListResponse(
                        rs.getLong("id"),
                        rs.getLong("topic_id"),
                        rs.getString("slug"),
                        rs.getString("question"),
                        QuestionDifficulty.valueOf(
                                rs.getString("difficulty")
                        )
                ))
                .list();
    }

    public Optional<QuestionDetailsResponse> findPublishedBySlug(
            String slug
    ) {
        String sql = """
                SELECT
                    id,
                    topic_id,
                    slug,
                    question,
                    answer,
                    explanation,
                    difficulty
                FROM question
                WHERE slug = :slug
                  AND status = 'PUBLISHED'
                """;

        return jdbcClient.sql(sql)
                .param("slug", slug)
                .query((rs, rowNum) -> new QuestionDetailsResponse(
                        rs.getLong("id"),
                        rs.getLong("topic_id"),
                        rs.getString("slug"),
                        rs.getString("question"),
                        rs.getString("answer"),
                        rs.getString("explanation"),
                        QuestionDifficulty.valueOf(
                                rs.getString("difficulty")
                        )
                ))
                .optional();
    }
}
