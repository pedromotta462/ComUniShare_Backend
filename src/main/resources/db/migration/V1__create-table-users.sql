CREATE TABLE users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    fone TEXT NOT NULL,
    address TEXT NOT NULL,
    role TEXT NOT NULL
);