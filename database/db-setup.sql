CREATE DATABASE thingappdb;

CREATE USER 'thingappuser'@'localhost' IDENTIFIED BY 'PLACEHOLDER_PASSWORD!';
GRANT ALL PRIVILEGES ON thingappdb.* TO 'thingappuser'@'localhost';
FLUSH PRIVILEGES;