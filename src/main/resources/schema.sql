-- Schema is created by Spring Boot on startup (spring.sql.init.mode=always).
-- Every statement is idempotent so restarts keep existing data.

CREATE TABLE IF NOT EXISTS user (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NULL,
    dob      DATETIME(6)  NULL,
    email    VARCHAR(255) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS course (
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    course_program     VARCHAR(255) NULL,
    course_code        VARCHAR(255) NULL,
    course_name        VARCHAR(255) NULL,
    course_description TEXT         NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS mycourses (
    user_id            BIGINT       NULL,
    course_program     VARCHAR(255) NULL,
    course_code        VARCHAR(255) NULL,
    course_name        VARCHAR(255) NULL,
    course_description TEXT         NULL
);

CREATE TABLE IF NOT EXISTS timetables (
    timetable_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id      BIGINT NULL,
    years        INT    NOT NULL,
    PRIMARY KEY (timetable_id)
);

CREATE TABLE IF NOT EXISTS timetable (
    user_id      BIGINT       NULL,
    timetable_id INT          NOT NULL,
    year         INT          NOT NULL,
    semester     VARCHAR(255) NOT NULL,
    course_name  VARCHAR(255) NOT NULL
);
