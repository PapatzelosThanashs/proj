-- Create Address table first because User depends on it
CREATE TABLE address (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    home_address VARCHAR(255),
    work_address VARCHAR(255)
);