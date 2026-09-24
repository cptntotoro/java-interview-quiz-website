CREATE TABLE question
(
    uuid          UUID                NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    topic_uuid    UUID                NOT NULL,
    slug          VARCHAR(150) UNIQUE NOT NULL,
    question      TEXT                NOT NULL,
    answer        TEXT                NOT NULL,
    explanation   TEXT,
    question_type VARCHAR(30)         NOT NULL,
    difficulty    VARCHAR(20)         NOT NULL,
    status        VARCHAR(20)         NOT NULL,
    created_at    TIMESTAMPTZ         NOT NULL,
    updated_at    TIMESTAMPTZ         NOT NULL,
    published_at  TIMESTAMPTZ,

    CONSTRAINT fk_question_topic FOREIGN KEY (topic_uuid) REFERENCES topic (uuid),

    CONSTRAINT chk_question_type
        CHECK ( question_type IN (
                              'SELF_ASSESSMENT',
                              'SINGLE_CHOICE',
                              'MULTIPLE_CHOICE',
                              'FREE_TEXT',
                              'DEFINITION_TO_TERM',
                              'TERM_TO_DEFINITION',
                              'TRUE_FALSE',
                              'CODE_OUTPUT',
                              'FIND_ERROR',
                              'MATCHING',
                              'ORDERING',
                              'SCENARIO'
                )
            ),

    CONSTRAINT chk_question_difficulty
        CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD')),

    CONSTRAINT chk_question_status
        CHECK (status IN ('DRAFT', 'PUBLISHED', 'ARCHIVED'))
);

CREATE INDEX idx_question_topic_status_id
    ON question (topic_uuid, status, uuid);

CREATE INDEX idx_question_status_id
    ON question (status, uuid);

CREATE INDEX idx_question_public_created
    ON question (status, created_at DESC, uuid DESC);

CREATE INDEX idx_question_topic_status_difficulty
    ON question (topic_uuid, status, difficulty, uuid);

CREATE INDEX idx_question_status_difficulty
    ON question (status, difficulty, uuid);