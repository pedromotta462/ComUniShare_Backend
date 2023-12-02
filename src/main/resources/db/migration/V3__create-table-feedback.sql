CREATE TABLE feedbacks (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    comment TEXT NOT NULL,
    rating INTEGER NOT NULL,
    user_id TEXT REFERENCES users(id),
    product_id TEXT REFERENCES products(id),
    created_at TIMESTAMPTZ NOT NULL
);
