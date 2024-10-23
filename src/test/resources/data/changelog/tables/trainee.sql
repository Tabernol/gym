CREATE TABLE IF NOT EXISTS trainee (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    date_of_birth DATE,
    address VARCHAR(256),

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
