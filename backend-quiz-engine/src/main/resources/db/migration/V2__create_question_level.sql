CREATE TABLE question_level
(
    id         SMALLINT     NOT NULL PRIMARY KEY,
    code       VARCHAR(50)  NOT NULL,
    name       VARCHAR(100) NOT NULL,
    sort_order INTEGER      NOT NULL,
    active     BOOLEAN      NOT NULL,

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