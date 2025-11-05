CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: order_items
CREATE TABLE order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE
);

INSERT INTO orders (customer_name, status, created_at) VALUES
('Alice', 'PENDING', NOW()),
('Bob', 'PROCESSING', NOW()),
('Charlie', 'SHIPPED', NOW());

-- Sample Order Items
INSERT INTO order_items (order_id, product_name, quantity, price) VALUES
(1, 'Laptop', 1, 999.99),
(1, 'Mouse', 2, 25.50),
(2, 'Keyboard', 1, 49.99),
(3, 'Monitor', 1, 199.99),
(3, 'HDMI Cable', 2, 10.00);
SELECT * FROM orders;
