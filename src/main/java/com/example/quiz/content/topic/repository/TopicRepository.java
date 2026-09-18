package com.example.quiz.content.topic.repository;

import com.example.quiz.content.common.ContentStatus;
import com.example.quiz.content.topic.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findBySlug(String slug);

    boolean existsBySlug(String slug);

    @Modifying
    @Query("""
        update Topic t
        set t.status = :status,
            t.updatedAt = :updatedAt,
            t.publishedAt = :publishedAt
        where t.id = :id
        """)
    int updateStatus(
            @Param("id") Long id,
            @Param("status") ContentStatus status,
            @Param("updatedAt") OffsetDateTime updatedAt,
            @Param("publishedAt") OffsetDateTime publishedAt
    );
}
