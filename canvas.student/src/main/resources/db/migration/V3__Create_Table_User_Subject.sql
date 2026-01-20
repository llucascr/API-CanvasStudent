CREATE TABLE user_subject_tb(
    user_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL,
    final_grade NUMERIC NOT NULL,
    PRIMARY KEY (user_id, subject_id),
    FOREIGN KEY (user_id) REFERENCES user_tb(user_id),
    FOREIGN KEY (subject_id) REFERENCES subject_tb(subject_id)
);