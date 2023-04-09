CREATE TABLE user (
    email VARCHAR(255) PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    password_hash VARCHAR(255) NOT NULL,
    profile_photo BLOB,
    google_id VARCHAR(255) UNIQUE,
    created_dtg DATETIME,
    updated_dtg DATETIME,
    last_login_dtg DATETIME,
    version BIGINT,
    INDEX (email),
    INDEX (google_id)
);
