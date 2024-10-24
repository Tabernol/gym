INSERT INTO users (first_name, last_name, username, password, is_active, role)
VALUES ('John', 'Doe', 'john.doe', 'root', TRUE, 'TRAINEE'),
       ('Jane', 'Smith', 'jane.smith', 'root', TRUE, 'TRAINEE'),
       ('Mike', 'Tyson', 'mike.tyson', 'root', TRUE, 'TRAINEE'),
       ('Serena', 'Williams', 'serena.williams', 'root', TRUE, 'TRAINEE');

INSERT INTO trainee (user_id, date_of_birth, address)
VALUES ((SELECT id FROM users WHERE username = 'john.doe'), '1990-05-15', '123 Main St, City, Country'),
       ((SELECT id FROM users WHERE username = 'jane.smith'), '1985-08-22', '456 Oak St, City, Country'),
       ((SELECT id FROM users WHERE username = 'mike.tyson'), '1966-06-30', '789 Pine St, City, Country'),
       ((SELECT id FROM users WHERE username = 'serena.williams'), '1981-09-26', '202 Cedar St, City, Country');
