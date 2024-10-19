INSERT INTO users (first_name, last_name, username, password, is_active, role)
VALUES ('Usain', 'Bolt', 'usain.bolt', 'root', TRUE, 'TRAINER'),
       ('Arnold', 'Schwarzenegger', 'arnold.schwarzenegger', 'root', TRUE, 'TRAINER'),
       ('Rich', 'Froning', 'rich.froning', 'root', TRUE, 'TRAINER');


INSERT INTO trainer (user_id, specialization_id)
VALUES ((SELECT id FROM users WHERE username = 'usain.bolt'),
        (SELECT id FROM training_type WHERE training_type_name = 'Cardio')),

       ((SELECT id FROM users WHERE username = 'arnold.schwarzenegger'),
        (SELECT id FROM training_type WHERE training_type_name = 'Bodybuilding')),

       ((SELECT id FROM users WHERE username = 'rich.froning'),
        (SELECT id FROM training_type WHERE training_type_name = 'CrossFit'));


