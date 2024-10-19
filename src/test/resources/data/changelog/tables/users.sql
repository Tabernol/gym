CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    username VARCHAR(66) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    is_active BOOLEAN NOT NULL,
    role VARCHAR(32) NOT NULL
    );
