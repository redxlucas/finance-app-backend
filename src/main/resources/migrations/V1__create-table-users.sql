CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    -- first_name VARCHAR NOT NULL,
    -- last_name VARCHAR NOT NULL,
    login VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

CREATE TABLE categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(7) NOT NULL
);