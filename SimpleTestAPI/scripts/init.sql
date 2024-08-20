-- DROP DATABASE IF EXISTS BootcampBeriJalan;

CREATE DATABASE IF NOT EXISTS BootcampBeriJalan;

USE BootcampBeriJalan;

CREATE TABLE IF NOT EXISTS buah (
    id INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    soft BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO buah (name, price, soft) VALUES 
('Apple', 5000, FALSE),
('Banana', 3000, FALSE),
('Orange', 4000, FALSE),
('Mango', 10000, FALSE),
('Pineapple', 15000, FALSE),
('Strawberry', 20000, FALSE),
('Watermelon', 12000, FALSE),
('Grapes', 8000, FALSE),
('Papaya', 6000, FALSE),
('Durian', 25000, FALSE);
