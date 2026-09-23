CREATE TABLE question
(
    uuid         UUID         NOT NULL PRIMARY KEY,
    topic_uuid   UUID         NOT NULL,
    slug         VARCHAR(150) NOT NULL,
    question     TEXT         NOT NULL,
    answer       TEXT         NOT NULL,
    explanation  TEXT,
    difficulty   VARCHAR(20)  NOT NULL,
    status       VARCHAR(20)  NOT NULL,
    created_at   TIMESTAMPTZ  NOT NULL,
    updated_at   TIMESTAMPTZ  NOT NULL,
    published_at TIMESTAMPTZ,

    CONSTRAINT uk_question_slug UNIQUE (slug),

    CONSTRAINT fk_question_topic FOREIGN KEY (topic_uuid) REFERENCES topic (uuid),

    CONSTRAINT chk_question_difficulty CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD')),

    CONSTRAINT chk_question_status CHECK (status IN ('DRAFT', 'PUBLISHED', 'ARCHIVED'))
);

CREATE INDEX idx_question_topic_status_id ON question (topic_uuid, status, uuid);

CREATE INDEX idx_question_status_id ON question (status, uuid);

CREATE INDEX idx_question_public_created ON question (status, created_at DESC, uuid DESC);