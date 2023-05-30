CREATE TABLE user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_user_id VARCHAR(255) NOT NULL,
    created_dtg DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    INDEX (user_id)
);