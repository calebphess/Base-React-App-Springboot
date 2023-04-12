CREATE TABLE user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_email VARCHAR(255) NOT NULL,
    created_dtg DATETIME NOT NULL,
    FOREIGN KEY (email) REFERENCES user(email),
    INDEX (email)
);