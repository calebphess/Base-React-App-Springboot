CREATE TABLE audit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    url VARCHAR(1000) NOT NULL,
    roles VARCHAR(1000) NOT NULL,
    operation VARCHAR(20) NOT NULL,
    audit_json LONGTEXT NOT NULL,
    event_dtg DATETIME NOT NULL,
    reviewed_dtg DATETIME,
    reviewer_user_id VARCHAR(255),
    source_ip VARCHAR(50),
    INDEX (user_id),
    INDEX (reviewer_user_id)
);
