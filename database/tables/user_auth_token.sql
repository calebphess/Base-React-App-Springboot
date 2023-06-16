CREATE TABLE user_auth_token (
    auth_token VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(45) NOT NULL,
    client_ip_address VARCHAR(50),
    issued_dtg DATETIME NOT NULL,
    expiration_dtg DATETIME NOT NULL,
    last_active_dtg DATETIME NOT NULL,
    is_valid TINYINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    INDEX (user_id)
);