CREATE TABLE user_profile (
    user_id VARCHAR(45) NOT NULL,
    display_name VARCHAR(255) PRIMARY KEY,
    profile_photo VARCHAR(255) NOT NULL,
    bio VARCHAR(1000) NULL,
    date_of_birth DATE NOT NULL,
    created_dtg DATETIME NOT NULL,
    updated_dtg DATETIME NOT NULL,
    version BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    INDEX (user_id)
);