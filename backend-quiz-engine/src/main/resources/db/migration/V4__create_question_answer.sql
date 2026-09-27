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