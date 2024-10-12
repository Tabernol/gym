CREATE TABLE IF NOT EXISTS training_type (
    id SERIAL PRIMARY KEY,
    training_type_name VARCHAR(64) NOT NULL UNIQUE
    );
