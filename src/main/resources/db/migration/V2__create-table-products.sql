CREATE TABLE products (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    type TEXT NOT NULL,
    price INTEGER NOT NULL,
    user_id TEXT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
