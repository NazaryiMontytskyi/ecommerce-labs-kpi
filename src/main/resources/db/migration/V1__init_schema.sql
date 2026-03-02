-- V1__init_schema.sql
-- Initial e-commerce schema

CREATE TABLE products (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,
    stock      INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO products (name, price, stock) VALUES
    ('Laptop',     999.99, 10),
    ('Mouse',       29.99, 50),
    ('Keyboard',    59.99, 30);
