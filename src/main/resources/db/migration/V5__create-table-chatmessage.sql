CREATE TABLE chat_messages (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    chat_id TEXT NOT NULL,
    sender_id TEXT NOT NULL,
    message TEXT NOT NULL,
    sent_at TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chats (id),
    FOREIGN KEY (sender_id) REFERENCES users (id)
);
