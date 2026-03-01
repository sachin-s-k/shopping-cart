CREATE TABLE carts (
    id BINARY(16) PRIMARY KEY
        DEFAULT (UUID_TO_BIN(UUID(),TRUE)),
    created_at DATE
        DEFAULT (CURDATE())
);