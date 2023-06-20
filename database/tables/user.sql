CREATE TABLE user (
    user_id VARCHAR(45) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    google_id VARCHAR(255) UNIQUE,
    last_login_dtg DATETIME,
    created_dtg DATETIME NOT NULL,
    updated_dtg DATETIME NOT NULL,
    version BIGINT NOT NULL,
    is_active TINYINT NOT NULL,
    INDEX (user_id),
    INDEX (google_id),
    INDEX (email)
);
