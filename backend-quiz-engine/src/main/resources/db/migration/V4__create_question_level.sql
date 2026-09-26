CREATE TABLE question_level
(
    id         SMALLINT    NOT NULL PRIMARY KEY,
    code       VARCHAR(50) NOT NULL,
    name       VARCHAR(100) NOT NULL,
    sort_order INTEGER     NOT NULL,
    active     BOOLEAN     NOT NULL,

    CONSTRAINT uk_question_level_code UNIQUE (code)
);

CREATE INDEX idx_question_level_active_sort
    ON question_level (active, sort_order, id);

INSERT INTO question_level (id, code, name, sort_order, active)
VALUES (1, 'INTERN', 'Intern', 10, TRUE),
       (2, 'JUNIOR', 'Junior', 20, TRUE),
       (3, 'JUNIOR_PLUS', 'Junior+', 30, TRUE),
       (4, 'MIDDLE', 'Middle', 40, TRUE),
       (5, 'SENIOR', 'Senior', 50, TRUE);


ALTER TABLE question
    ADD COLUMN level_id SMALLINT;

ALTER TABLE question
    ADD COLUMN reference_answer TEXT;

UPDATE question
SET level_id = CASE difficulty
                   WHEN 'EASY' THEN 1
                   WHEN 'MEDIUM' THEN 2
                   WHEN 'HARD' THEN 3
    END;

UPDATE question
SET reference_answer = answer;

ALTER TABLE question
    ALTER COLUMN level_id SET NOT NULL;

ALTER TABLE question
    ADD CONSTRAINT fk_question_level
        FOREIGN KEY (level_id) REFERENCES question_level (id);

ALTER TABLE question
    ALTER COLUMN reference_answer DROP NOT NULL;

ALTER TABLE question
DROP CONSTRAINT chk_question_difficulty;

ALTER TABLE question
DROP COLUMN difficulty;

ALTER TABLE question
DROP COLUMN answer;

ALTER TABLE question
    ADD CONSTRAINT chk_question_type
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
            ));


DROP INDEX idx_question_topic_status_difficulty;
DROP INDEX idx_question_status_difficulty;

CREATE INDEX idx_question_topic_status_level
    ON question (topic_uuid, status, level_id, uuid);

CREATE INDEX idx_question_status_level_type
    ON question (status, level_id, question_type, uuid);


CREATE TABLE question_option
(
    uuid         UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    question_uuid UUID       NOT NULL,
    text         TEXT        NOT NULL,
    correct      BOOLEAN     NOT NULL,
    sort_order   INTEGER     NOT NULL,

    CONSTRAINT fk_question_option_question
        FOREIGN KEY (question_uuid)
            REFERENCES question (uuid)
            ON DELETE CASCADE,

    CONSTRAINT uk_question_option_order
        UNIQUE (question_uuid, sort_order)
);

CREATE INDEX idx_question_option_question
    ON question_option (question_uuid, sort_order);


CREATE TABLE question_matching_pair
(
    uuid         UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    question_uuid UUID       NOT NULL,
    left_text    TEXT        NOT NULL,
    right_text   TEXT        NOT NULL,
    sort_order   INTEGER     NOT NULL,

    CONSTRAINT fk_question_matching_question
        FOREIGN KEY (question_uuid)
            REFERENCES question (uuid)
            ON DELETE CASCADE,

    CONSTRAINT uk_question_matching_order
        UNIQUE (question_uuid, sort_order)
);

CREATE INDEX idx_question_matching_question
    ON question_matching_pair (question_uuid, sort_order);


CREATE TABLE question_order_item
(
    uuid          UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    question_uuid  UUID       NOT NULL,
    text           TEXT        NOT NULL,
    correct_order  INTEGER     NOT NULL,

    CONSTRAINT fk_question_order_question
        FOREIGN KEY (question_uuid)
            REFERENCES question (uuid)
            ON DELETE CASCADE,

    CONSTRAINT uk_question_order
        UNIQUE (question_uuid, correct_order)
);

CREATE INDEX idx_question_order_question
    ON question_order_item (question_uuid, correct_order);