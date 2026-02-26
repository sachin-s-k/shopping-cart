CREATE TABLE users
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE addresses
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    column_name INT NULL,
    city VARCHAR(255) NULL,
    zip VARCHAR(255) NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT addresses_users_id_fk
        FOREIGN KEY (user_id) REFERENCES users (id)
);
