CREATE TABLE question
(
    uuid             UUID                NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    topic_uuid       UUID                NOT NULL,
    slug             VARCHAR(150) UNIQUE NOT NULL,
    question         TEXT                NOT NULL,
    reference_answer TEXT,
    explanation      TEXT,
    question_type    VARCHAR(30)         NOT NULL,
    level_id         SMALLINT            NOT NULL,
    status           VARCHAR(20)         NOT NULL,
    created_at       TIMESTAMPTZ         NOT NULL,
    updated_at       TIMESTAMPTZ         NOT NULL,
    published_at     TIMESTAMPTZ,

    CONSTRAINT uk_question_slug UNIQUE (slug),

    CONSTRAINT fk_question_topic
        FOREIGN KEY (topic_uuid)
            REFERENCES topic (uuid),

    CONSTRAINT fk_question_level
        FOREIGN KEY (level_id)
            REFERENCES question_level (id),

    CONSTRAINT chk_question_type
        CHECK (question_type IN (
                                 'SELF_ASSESSMENT',
                                 'FREE_TEXT',
                                 'SINGLE_CHOICE',
                                 'MULTIPLE_CHOICE',
                                 'DEFINITION_TO_TERM',
                                 'TRUE_FALSE',
                                 'FIND_ERROR',
                                 'MATCHING',
                                 'ORDERING'
            )),

    CONSTRAINT chk_question_status
        CHECK (status IN ('DRAFT', 'PUBLISHED', 'ARCHIVED'))
);

CREATE INDEX idx_question_topic_status_id
    ON question (topic_uuid, status, uuid);

CREATE INDEX idx_question_status_id
    ON question (status, uuid);

CREATE INDEX idx_question_public_created
    ON question (status, created_at DESC, uuid DESC);

CREATE INDEX idx_question_topic_status_level
    ON question (topic_uuid, status, level_id, uuid);

CREATE INDEX idx_question_status_level_type
    ON question (status, level_id, question_type, uuid);