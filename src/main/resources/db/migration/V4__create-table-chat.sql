CREATE TABLE chats (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    user1_id TEXT NOT NULL,
    user2_id TEXT NOT NULL,
    FOREIGN KEY (user1_id) REFERENCES users (id),
    FOREIGN KEY (user2_id) REFERENCES users (id)
);
