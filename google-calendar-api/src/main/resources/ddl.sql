-- Create the products table if not present
CREATE TABLE IF NOT EXISTS products (
  id        SERIAL PRIMARY KEY,
  name      VARCHAR(40) NOT NULL,
  stock     BIGINT
);

DELETE FROM products;

INSERT INTO products (name, stock) values ('Apple', 10);
INSERT INTO products (name, stock) values ('Orange', 10);
INSERT INTO products (name, stock) values ('Pear', 10);
