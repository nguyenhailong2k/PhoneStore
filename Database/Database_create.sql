CREATE DATABASE IF NOT EXISTS phonestore CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE phonestore;

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    image VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create admins table
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default admin account (username: admin, password: admin123)
INSERT INTO admins (username, password) VALUES ('admin', '123');

-- Insert sample products
INSERT INTO products (name, price, image) VALUES
('iPhone 15 Pro Max', 29990000, 'iphone15promax.jpg'),
('Samsung Galaxy S24 Ultra', 26990000, 'galaxys24ultra.jpg'),
('Xiaomi 14 Ultra', 22990000, 'xiaomi14ultra.jpg'),
('OnePlus 12', 18990000, 'oneplus12.jpg'),
('Google Pixel 8 Pro', 21990000, 'pixel8pro.jpg'),
('Oppo Find X7 Ultra', 24990000, 'oppofindx7ultra.jpg'),
('Vivo X100 Pro', 23990000, 'vivox100pro.jpg'),
('Realme GT 5 Pro', 15990000, 'realmegtpro.jpg'),
('iPhone 14 Pro', 29990000, 'iphone14pro.jpg'),
('Samsung Galaxy S23', 21990000, 'samsung_s23.jpg'),
('Xiaomi Redmi Note 12', 6990000, 'redmi_note12.jpg'),
('OPPO Reno 10', 8990000, 'oppo_reno10.jpg');

-- Create indexes for better performance
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_price ON products(price);
CREATE INDEX idx_admins_username ON admins(username);




-- Add category_id to products table
ALTER TABLE products ADD COLUMN category_id INT;
ALTER TABLE products ADD COLUMN description TEXT;
ALTER TABLE products ADD COLUMN stock_quantity INT DEFAULT 0;
ALTER TABLE products ADD COLUMN brand VARCHAR(100);
ALTER TABLE products ADD COLUMN specs TEXT;

-- Create categories table
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    total_amount DECIMAL(15,2) NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'SHIPPING', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create order_items table
CREATE TABLE IF NOT EXISTS order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    price DECIMAL(15,2) NOT NULL,
    quantity INT NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Create reviews table
CREATE TABLE IF NOT EXISTS reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Create contacts table
CREATE TABLE IF NOT EXISTS contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    subject VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    status ENUM('NEW', 'READ', 'REPLIED') DEFAULT 'NEW',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample categories
INSERT INTO categories (name, description, image, active) VALUES
('iPhone', 'Điện thoại iPhone chính hãng', 'iphone-category.jpg', TRUE),
('Samsung', 'Điện thoại Samsung Galaxy series', 'samsung-category.jpg', TRUE),
('Xiaomi', 'Điện thoại Xiaomi giá tốt', 'xiaomi-category.jpg', TRUE),
('Oppo', 'Điện thoại Oppo thiết kế đẹp', 'oppo-category.jpg', TRUE),
('Vivo', 'Điện thoại Vivo camera selfie', 'vivo-category.jpg', TRUE);

INSERT INTO categories (name, description, image, active) values
('OnePlus', 'Điện thoại OnePlus chính hãng', 'oneplus-category.jpg', TRUE),
('Google', 'Điện thoại Google series', 'google-category.jpg', TRUE),
('Realme', 'Điện thoại Realme camera selfie', 'realme-category.jpg', TRUE);
-- Update products with categories
UPDATE products SET category_id = 1, brand = 'Apple', stock_quantity = 10 WHERE name LIKE '%iPhone%';
UPDATE products SET category_id = 2, brand = 'Samsung', stock_quantity = 15 WHERE name LIKE '%Samsung%';
UPDATE products SET category_id = 3, brand = 'Xiaomi', stock_quantity = 20 WHERE name LIKE '%Xiaomi%';
UPDATE products SET category_id = 4, brand = 'Oppo', stock_quantity = 12 WHERE name LIKE '%Oppo%';
UPDATE products SET category_id = 5, brand = 'Vivo', stock_quantity = 8 WHERE name LIKE '%Vivo%';
UPDATE products SET category_id = 6, brand = 'OnePlus', stock_quantity = 8 WHERE name LIKE '%OnePlus%';
UPDATE products SET category_id = 7, brand = 'Google', stock_quantity = 8 WHERE name LIKE '%Google%';
UPDATE products SET category_id = 8, brand = 'Realme', stock_quantity = 8 WHERE name LIKE '%Realme%';

-- Add foreign key constraint
ALTER TABLE products ADD FOREIGN KEY (category_id) REFERENCES categories(id);

-- Create indexes for better performance
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_date ON orders(order_date);
CREATE INDEX idx_reviews_product ON reviews(product_id);
CREATE INDEX idx_reviews_approved ON reviews(approved);
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_brand ON products(brand);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    address TEXT
);

-- iPhone 15 Pro Max
UPDATE products SET 
    description = 'iPhone 15 Pro Max với thiết kế titan siêu nhẹ, chip A17 Pro mạnh mẽ và camera 48MP chuyên nghiệp.',
    specs = 'Màn hình: 6.7 inch OLED Super Retina XDR\nChip: Apple A17 Pro\nRAM: 8GB\nCamera: 48MP + 12MP + 12MP\nPin: 4422mAh\nSạc nhanh: 20W',
    stock_quantity = 12
WHERE name = 'iPhone 15 Pro Max';

-- Samsung Galaxy S24 Ultra
UPDATE products SET 
    description = 'Galaxy S24 Ultra sở hữu màn hình Dynamic AMOLED 2X, bút S-Pen tích hợp và camera 200MP cực khủng.',
    specs = 'Màn hình: 6.8 inch QHD+ AMOLED\nChip: Snapdragon 8 Gen 3\nRAM: 12GB\nCamera: 200MP + 12MP + 10MP + 10MP\nPin: 5000mAh\nSạc nhanh: 45W',
    stock_quantity = 10
WHERE name = 'Samsung Galaxy S24 Ultra';

-- Xiaomi 14 Ultra
UPDATE products SET 
    description = 'Xiaomi 14 Ultra là flagship mới nhất với cụm camera Leica cao cấp và hiệu năng mạnh mẽ.',
    specs = 'Màn hình: 6.73 inch AMOLED 120Hz\nChip: Snapdragon 8 Gen 3\nRAM: 12GB\nCamera: 50MP Leica Quad\nPin: 5000mAh\nSạc nhanh: 90W',
    stock_quantity = 15
WHERE name = 'Xiaomi 14 Ultra';

-- OnePlus 12
UPDATE products SET 
    description = 'OnePlus 12 là lựa chọn hiệu năng cao giá hợp lý, camera Hasselblad, sạc siêu nhanh.',
    specs = 'Màn hình: 6.82 inch AMOLED QHD+\nChip: Snapdragon 8 Gen 3\nRAM: 16GB\nCamera: 50MP + 48MP + 64MP\nPin: 5400mAh\nSạc nhanh: 100W',
    stock_quantity = 18
WHERE name = 'OnePlus 12';

-- Google Pixel 8 Pro
UPDATE products SET 
    description = 'Pixel 8 Pro với chip Tensor G3, camera AI mạnh mẽ và hỗ trợ cập nhật phần mềm dài hạn từ Google.',
    specs = 'Màn hình: 6.7 inch OLED LTPO\nChip: Google Tensor G3\nRAM: 12GB\nCamera: 50MP + 48MP + 48MP\nPin: 5050mAh\nSạc nhanh: 30W',
    stock_quantity = 9
WHERE name = 'Google Pixel 8 Pro';

-- Oppo Find X7 Ultra
UPDATE products SET 
    description = 'Flagship cao cấp của Oppo với camera xoay linh hoạt, màn hình sắc nét và sạc siêu nhanh.',
    specs = 'Màn hình: 6.82 inch AMOLED LTPO\nChip: Snapdragon 8 Gen 3\nRAM: 16GB\nCamera: 50MP Quad Hasselblad\nPin: 5000mAh\nSạc nhanh: 100W',
    stock_quantity = 14
WHERE name = 'Oppo Find X7 Ultra';

-- Vivo X100 Pro
UPDATE products SET 
    description = 'Vivo X100 Pro nổi bật với camera ZEISS, cấu hình mạnh và thiết kế sang trọng.',
    specs = 'Màn hình: 6.78 inch AMOLED 120Hz\nChip: Dimensity 9300\nRAM: 16GB\nCamera: 50MP ZEISS Triple\nPin: 5400mAh\nSạc nhanh: 120W',
    stock_quantity = 11
WHERE name = 'Vivo X100 Pro';

-- Realme GT 5 Pro
UPDATE products SET 
    description = 'Flagship mạnh mẽ giá tốt đến từ Realme, hiệu năng cao, màn hình đẹp và sạc nhanh.',
    specs = 'Màn hình: 6.78 inch AMOLED 144Hz\nChip: Snapdragon 8 Gen 3\nRAM: 12GB\nCamera: 50MP + 8MP + 2MP\nPin: 5400mAh\nSạc nhanh: 100W',
    stock_quantity = 16
WHERE name = 'Realme GT 5 Pro';

-- iPhone 14 Pro
UPDATE products SET 
    description = 'iPhone 14 Pro vẫn là lựa chọn tốt với hiệu năng mượt mà, camera chất lượng và Dynamic Island.',
    specs = 'Màn hình: 6.1 inch Super Retina XDR\nChip: Apple A16 Bionic\nRAM: 6GB\nCamera: 48MP + 12MP + 12MP\nPin: 3200mAh\nSạc nhanh: 20W',
    stock_quantity = 10
WHERE name = 'iPhone 14 Pro';

-- Samsung Galaxy S23
UPDATE products SET 
    description = 'Samsung Galaxy S23 có kích thước gọn, cấu hình tốt và camera sắc nét.',
    specs = 'Màn hình: 6.1 inch AMOLED FHD+\nChip: Snapdragon 8 Gen 2\nRAM: 8GB\nCamera: 50MP + 12MP + 10MP\nPin: 3900mAh\nSạc nhanh: 25W',
    stock_quantity = 13
WHERE name = 'Samsung Galaxy S23';

-- Xiaomi Redmi Note 12
UPDATE products SET 
    description = 'Redmi Note 12 là mẫu điện thoại tầm trung nổi bật với màn hình AMOLED và pin lớn.',
    specs = 'Màn hình: 6.67 inch AMOLED 120Hz\nChip: Snapdragon 685\nRAM: 6GB\nCamera: 50MP + 8MP + 2MP\nPin: 5000mAh\nSạc nhanh: 33W',
    stock_quantity = 30
WHERE name = 'Xiaomi Redmi Note 12';

-- OPPO Reno 10
UPDATE products SET 
    description = 'Oppo Reno 10 sở hữu thiết kế đẹp, camera chân dung chất lượng và hiệu năng ổn.',
    specs = 'Màn hình: 6.7 inch AMOLED\nChip: Dimensity 7050\nRAM: 8GB\nCamera: 64MP + 32MP + 8MP\nPin: 5000mAh\nSạc nhanh: 67W',
    stock_quantity = 20
WHERE name = 'OPPO Reno 10';

CREATE TABLE brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);

INSERT INTO brands (name) VALUES 
('Apple'),
('Samsung'),
('Xiaomi'),
('OPPO'),
('Vivo'),
('Realme'),
('Sony'),
('Nokia'),
('Huawei'),
('Asus');

ALTER TABLE products ADD COLUMN active BOOLEAN DEFAULT TRUE;

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    customer_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    address TEXT,
    total_amount DOUBLE NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- phải có dấu phẩy trước dòng dưới
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE SET NULL
);

ALTER TABLE orders
ADD CONSTRAINT fk_customer
FOREIGN KEY (customer_id) REFERENCES customers(id)
ON DELETE SET NULL;

