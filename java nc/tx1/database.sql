CREATE DATABASE IF NOT EXISTS tx1_db;
USE tx1_db;

CREATE TABLE IF NOT EXISTS Account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL
);

INSERT INTO Account (username, password) VALUES
('user1', '123456'),
('user2', '123456'),
('user3', '123456'),
('user4', '123456'),
('user5', '123456');


select * From Account