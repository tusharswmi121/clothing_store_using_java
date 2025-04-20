CREATE DATABASE IF NOT EXISTS clothing_store;

USE clothing_store;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    price DOUBLE,
    description TEXT
);

CREATE TABLE payment (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    product_id INT,
    payment_method VARCHAR(50),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);



ALTER TABLE products ADD COLUMN image_path VARCHAR(255);
UPDATE products SET image_path = 'images/image1.jpeg' WHERE name = 'T-Shirt';
UPDATE products SET image_path = 'images/image2.jpeg' WHERE name = 'Jeans';
UPDATE products SET image_path = 'images/image3.jpeg' WHERE name = 'Jacket';



-- Add quantity column if not already present
ALTER TABLE products ADD COLUMN quantity INT DEFAULT 0;

-- Update quantity to 6 for all products
UPDATE products SET quantity = 6;
-- Insert extra 3 products with image paths


-- TRUNCATE TABLE products;

INSERT INTO products (name, price, description, image_path, quantity) VALUES
('Classic White Tee', 24.99, 'Cotton T-shirt', 'images/image1.jpeg', 6),
('Denim Jacket', 89.99, 'Denim jacket', 'images/image2.jpeg', 6),
('Black Skinny Jeans', 59.99, 'Slim fit denim jeans', 'images/image3.jpeg', 6),
('Floral Summer Dress', 65.99, 'Floral pattern dress', 'images/image4.jpg', 6),
('Oversized Sweater', 45.99, 'Cozy oversized sweater', 'images/image5.jpeg', 6),
('Striped Polo Shirt', 39.99, 'Casual striped polo', 'images/image6.jpeg', 6),
('Ocean Breeze Perfume', 49.99, 'Floral and oceanic scent', 'images/perfume1.jpeg', 6),
('Vanilla Musk', 59.99, 'Warm vanilla musk fragrance', 'images/perfume2.jpeg', 6),
('Rose Petal', 54.99, 'Elegant rose scent', 'images/perfume3.jpeg', 6),
('Citrus Zest', 44.99, 'Citrus and fresh fragrance', 'images/perfume4.jpeg', 6),
('Woody Amber', 64.99, 'Woodsy and amber scent', 'images/perfume5.jpeg', 6),
('Night Bloom', 74.99, 'Warm floral nighttime scent', 'images/perfume6.jpeg', 6),
('Canvas Sneakers', 49.99, 'Comfortable sneakers for everyday', 'images/shoe1.jpeg', 6),
('Running Shoes', 69.99, 'High-performance running shoes', 'images/shoe2.jpeg', 6),
('Leather Boots', 89.99, 'Stylish leather boots', 'images/shoe3.jpeg', 6),
('Slip-on Loafers', 59.99, 'Easy slip-on casual loafers', 'images/shoe4.jpeg', 6),
('High Heels', 79.99, 'Elegant high heels', 'images/shoe5.jpeg', 6),
('Sandals', 39.99, 'Summer sandals', 'images/shoe6.jpeg', 6),
('Smart Watch A1', 149.99, 'Feature-packed smartwatch', 'images/watch1.jpeg', 6),
('Smart Watch B2', 169.99, 'Advanced smartwatch', 'images/watch2.jpeg', 6),
('Analog Watch X', 129.99, 'Classic analog watch', 'images/watch3.jpeg', 6),
('Sport Watch Z', 139.99, 'Sport-focused digital watch', 'images/watch4.jpeg', 6),
('Luxury Watch Pro', 199.99, 'Luxury watch with precision', 'images/watch5.jpeg', 6),
('Digital Watch Y9', 89.99, 'Stylish digital watch', 'images/watch6.jpeg', 6),
('Phone X10', 699.99, 'High-end smartphone with great features', 'images/phone1.jpeg', 6),
('Phone G5', 499.99, 'Affordable phone with decent specs', 'images/phone2.jpeg', 6),
('Phone UltraMax', 899.99, 'Flagship smartphone with excellent camera', 'images/phone3.jpeg', 6),
('Phone Compact', 399.99, 'Compact phone for everyday use', 'images/phone4.jpeg', 6),
('Phone Xtreme', 799.99, 'Premium phone with cutting-edge technology', 'images/phone5.jpeg', 6),
('Phone Lite', 299.99, 'Budget-friendly smartphone', 'images/phone6.jpeg', 6),
('Wireless Earbuds A', 89.99, 'Wireless earbuds with clear sound', 'images/earbuds1.jpeg', 6),
('Noise-Cancel Buds', 109.99, 'Noise-cancelling wireless earbuds', 'images/earbuds2.jpeg', 6),
('Bass Boost Buds', 99.99, 'Earbuds with bass enhancement', 'images/earbuds3.jpeg', 6),
('Comfort Fit Buds', 79.99, 'Comfortable and secure earbuds', 'images/earbuds4.jpeg', 6),
('Gaming Buds', 129.99, 'Perfect for gaming with low latency', 'images/earbuds5.jpeg', 6),
('Mini Buds', 59.99, 'Compact and powerful earbuds', 'images/earbuds6.jpeg', 6),
('Insulated Bottle', 29.99, 'Thermal bottle for hot and cold drinks', 'images/bottle1.jpeg', 6),
('Glass Bottle', 19.99, 'Stylish glass bottle for water', 'images/bottle2.jpeg', 6),
('Stainless Steel Bottle', 34.99, 'Durable stainless steel bottle', 'images/bottle3.jpeg', 6),
('Sport Sipper', 24.99, 'Portable sports water sipper', 'images/bottle4.jpeg', 6),
('Infuser Bottle', 39.99, 'Bottle with fruit infuser', 'images/bottle5.jpg', 6),
('Frosted Bottle', 22.99, 'Frosted finish bottle for style', 'images/bottle6.jpeg', 6);


-- Add a category column to store product type
ALTER TABLE products ADD COLUMN category VARCHAR(100);

-- Example update: Assign categories to existing products
UPDATE products SET category = 'Clothes' WHERE name IN ('T-Shirt', 'Jeans', 'Jacket', 'Sneakers', 'Cap', 'Jacket');
UPDATE products SET category = 'Perfumes' WHERE name IN ('Ocean Breeze Perfume', 'Vanilla Musk', 'Rose Petal', 'Citrus Zest', 'Woody Amber', 'Night Bloom');
UPDATE products SET category = 'Shoes' WHERE name IN ('Canvas Sneakers', 'Running Shoes', 'Leather Boots', 'Slip-on Loafers', 'High Heels', 'Sandals');
UPDATE products SET category = 'Watches' WHERE name IN ('Smart Watch A1', 'Smart Watch B2', 'Analog Watch X', 'Sport Watch Z', 'Luxury Watch Pro', 'Digital Watch Y9');
UPDATE products SET category = 'Phones' WHERE name IN ('Phone X10', 'Phone G5', 'Phone UltraMax', 'Phone Compact', 'Phone Xtreme', 'Phone Lite');
UPDATE products SET category = 'Earbuds' WHERE name IN ('Wireless Earbuds A', 'Noise-Cancel Buds', 'Bass Boost Buds', 'Comfort Fit Buds', 'Gaming Buds', 'Mini Buds');
UPDATE products SET category = 'Bottles' WHERE name IN ('Insulated Bottle', 'Glass Bottle', 'Stainless Steel Bottle', 'Sport Sipper', 'Infuser Bottle', 'Frosted Bottle');


UPDATE products SET category = 'Clothes' WHERE name = 'Classic White Tee';
UPDATE products SET category = 'Clothes' WHERE name = 'Denim Jacket';
UPDATE products SET category = 'Clothes' WHERE name = 'Black Skinny Jeans';
UPDATE products SET category = 'Clothes' WHERE name = 'Floral Summer Dress';
UPDATE products SET category = 'Clothes' WHERE name = 'Oversized Sweater';
UPDATE products SET category = 'Clothes' WHERE name = 'Striped Polo Shirt';


SELECT * FROM users;
SELECT * FROM products;
SELECT * FROM payment;
