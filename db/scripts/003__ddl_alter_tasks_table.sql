ALTER TABLE tasks ADD user_id INT NOT NULL REFERENCES users(id);