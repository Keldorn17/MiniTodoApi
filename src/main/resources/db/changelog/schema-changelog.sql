-- liquibase formatted sql

-- changeset PataiZoltan:create-user-table
-- Creates User table
-- rollback DROP TABLE user
CREATE TABLE user
(
    user_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) UNIQUE NOT NULL,
    name     VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(255) NOT NULL
);

-- changeset PataiZoltan:create-todo-table
-- Creates Todo table
-- rollback DROP TABLE todo
CREATE TABLE todo
(
    todo_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255),
    description VARCHAR(255),
    due_date    DATETIME,
    completed   BOOLEAN,
    priority    INT NOT NULL,
    user_id     BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    INDEX       user_id_idx (user_id)
);