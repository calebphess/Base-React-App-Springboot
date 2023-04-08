CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    password_hash VARCHAR(255) NOT NULL,
    profile_photo BLOB,
    google_id VARCHAR(255) UNIQUE,
    INDEX (email)
);
