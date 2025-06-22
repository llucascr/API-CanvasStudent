CREATE TABLE subject_tb(
    subject_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    semester SMALLINT NOT NULL,
    status VARCHAR(255) NOT NULL CHECK (status IN ('CURSANDO', 'CURSADO'))
);

CREATE TABLE user_tb(
    user_id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    university VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL
);

CREATE TABLE user_subject_tb(
    user_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    final_grade NUMERIC NOT NULL,
    PRIMARY KEY (user_id, subject_id),
    FOREIGN KEY (user_id) REFERENCES user_tb(user_id),
    FOREIGN KEY (subject_id) REFERENCES subject_tb(subject_id)
);

CREATE TABLE grade_tb(
    grade_id BIGINT PRIMARY KEY,
    grade NUMERIC NOT NULL,
    weight NUMERIC NOT NULL,
    user_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    CONSTRAINT fk_user_subject FOREIGN KEY (user_id,subject_id) REFERENCES user_subject_tb(user_id,subject_id)
);
