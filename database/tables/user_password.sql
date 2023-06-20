CREATE TABLE user_password (
    email VARCHAR(255) PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL,
    created_dtg DATETIME NOT NULL,
    updated_dtg DATETIME NOT NULL,
    login_attempts INT NOT NULL,
    reset_required TINYINT NOT NULL,
    version BIGINT NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email),
    INDEX (email)
);