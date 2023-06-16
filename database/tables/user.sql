CREATE TABLE user (
    user_id VARCHAR(45) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    google_id VARCHAR(255) UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    date_of_birth DATETIME,
    password_hash VARCHAR(255) NOT NULL,
    password_date DATETIME NOT NULL,
    profile_photo BLOB NOT NULL,
    last_login_dtg DATETIME,
    created_dtg DATETIME NOT NULL,
    updated_dtg DATETIME NOT NULL,
    version BIGINT NOT NULL,
    INDEX (user_id),
    INDEX (google_id)
);
