CREATE TABLE user
(
    user_id   INT AUTO_INCREMENT NOT NULL,
    user_name VARCHAR(20)        NOT NULL,
    password  VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (user_id)
);

ALTER TABLE user
    ADD CONSTRAINT uc_user_user UNIQUE (user_id);

ALTER TABLE user
    ADD CONSTRAINT uc_user_user_name UNIQUE (user_name);
CREATE TABLE student
(
    student_id   INT AUTO_INCREMENT NOT NULL,
    student_name VARCHAR(255)       NOT NULL,
    student_code VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_student PRIMARY KEY (student_id)
);

ALTER TABLE student
    ADD CONSTRAINT uc_student_student UNIQUE (student_id);
CREATE TABLE student_info_model
(
    info_id       INT AUTO_INCREMENT NOT NULL,
    student_id    INT                NOT NULL,
    address       VARCHAR(255)       NULL,
    average_score DOUBLE             NULL,
    date_of_birth datetime           NULL,
    CONSTRAINT pk_student_info_model PRIMARY KEY (info_id)
);

ALTER TABLE student_info_model
    ADD CONSTRAINT uc_student_info_model_student UNIQUE (student_id);

ALTER TABLE student_info_model
    ADD CONSTRAINT FK_STUDENT_INFO_MODEL_ON_STUDENT FOREIGN KEY (student_id) REFERENCES student (student_id);