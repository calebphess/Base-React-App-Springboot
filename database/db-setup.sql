-- Create the app database
CREATE DATABASE thingappdb;

-- create the application user who can only perform CRUD on tables and has no admin privs
CREATE USER 'thingappuser'@'localhost' IDENTIFIED BY 'PLACEHOLDER_PASSWORD!';
GRANT SELECT, INSERT, UPDATE, DELETE ON thingappdb.* TO 'thingappuser'@'localhost';
FLUSH PRIVILEGES;