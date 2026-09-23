package com.example.quiz.content.common;

/**
 * Статус контента
 */
public enum ContentStatus {

    DRAFT,
    PUBLISHED,
    ARCHIVED;

    public boolean canPublish() {
        return this == DRAFT;
    }

    public boolean canArchive() {
        return this == DRAFT || this == PUBLISHED;
    }
}
