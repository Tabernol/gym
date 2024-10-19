CREATE TABLE IF NOT EXISTS trainer_trainee (
    trainer_id BIGINT  NOT NULL,
    trainee_id BIGINT NOT NULL,

    PRIMARY KEY (trainer_id, trainee_id),

    FOREIGN KEY (trainer_id) REFERENCES trainer(id),
    FOREIGN KEY (trainee_id) REFERENCES trainee(id)
    );
