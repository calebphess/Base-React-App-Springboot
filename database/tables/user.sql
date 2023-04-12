CREATE TABLE user (
    email VARCHAR(255) PRIMARY KEY,
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
    updated_email VARCHAR(255) NOT NULL,
    version BIGINT NOT NULL,
    INDEX (email),
    INDEX (google_id)
);
