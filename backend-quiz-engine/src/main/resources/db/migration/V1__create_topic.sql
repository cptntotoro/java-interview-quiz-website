CREATE TABLE topic
(
    uuid         UUID         NOT NULL PRIMARY KEY DEFAULT gen_random_uuid(),
    slug         VARCHAR(150) NOT NULL,
    name         VARCHAR(255) NOT NULL,
    description  TEXT,
    parent_uuid  UUID,
    status       VARCHAR(20)  NOT NULL,
    sort_order   INTEGER      NOT NULL DEFAULT 0,
    created_at   TIMESTAMPTZ  NOT NULL,
    updated_at   TIMESTAMPTZ  NOT NULL,
    published_at TIMESTAMPTZ,

    CONSTRAINT uk_topic_slug UNIQUE (slug),

    CONSTRAINT fk_topic_parent FOREIGN KEY (parent_uuid) REFERENCES topic (uuid),

    CONSTRAINT chk_topic_status CHECK (status IN ('DRAFT', 'PUBLISHED', 'ARCHIVED'))
);

CREATE INDEX idx_topic_parent_id ON topic (parent_uuid);

CREATE INDEX idx_topic_status_sort ON topic (status, sort_order, uuid);