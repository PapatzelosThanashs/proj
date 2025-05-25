-- Create User table
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    surname VARCHAR(12) NOT NULL,
    birthdate DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    address_id BIGINT UNIQUE,
    version BIGINT DEFAULT 0,
    CONSTRAINT fk_user_address
        FOREIGN KEY (address_id)
        REFERENCES address(id)
        ON DELETE CASCADE
);

