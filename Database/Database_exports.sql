-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: phonestore
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `idx_admins_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'admin','123','2025-06-09 15:55:41');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'Apple'),(10,'Asus'),(9,'Huawei'),(8,'Nokia'),(4,'OPPO'),(6,'Realme'),(2,'Samsung'),(7,'Sony'),(5,'Vivo'),(3,'Xiaomi');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'iPhone','Điện thoại iPhone chính hãng','iphone-category.jpg',1,'2025-06-11 11:17:40'),(2,'Samsung','Điện thoại Samsung Galaxy series','samsung-category.jpg',1,'2025-06-11 11:17:40'),(3,'Xiaomi','Điện thoại Xiaomi giá tốt','xiaomi-category.jpg',1,'2025-06-11 11:17:40'),(4,'Oppo','Điện thoại Oppo thiết kế đẹp','oppo-category.jpg',1,'2025-06-11 11:17:40'),(5,'Vivo','Điện thoại Vivo camera selfie','vivo-category.jpg',1,'2025-06-11 11:17:40'),(6,'OnePlus','Điện thoại OnePlus chính hãng','oneplus-category.jpg',1,'2025-06-11 12:27:18'),(7,'Google','Điện thoại Google series','google-category.jpg',1,'2025-06-11 12:27:18'),(8,'Realme','Điện thoại Realme camera selfie','realme-category.jpg',1,'2025-06-11 12:27:18');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subject` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` enum('NEW','READ','REPLIED') COLLATE utf8mb4_unicode_ci DEFAULT 'NEW',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'ado','bleachfc134k@gmail.com','123456','0394240586','Dong Phu, Xuan Phu, Yen Dung\r\nĐông Phú, Xuân Phú, Yên Dũng'),(2,'Long Dai Ca','bleachfc2k@gmail.com','123','0123456789','Ha Noi'),(3,'Nguyễn Văn Toán','bleachfc20k@gmail.com','123','0394240586','Dong Phu, Xuan Phu, Yen Dung'),(4,'admin','admin123@gmail.com','123','0123456789','Ha Noi');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `product_id` int NOT NULL,
  `product_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(15,2) NOT NULL,
  `quantity` int NOT NULL,
  `subtotal` decimal(15,2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (19,18,12,'OPPO Reno 10',8990000.00,1,8990000.00),(20,19,31,'ASUS ROG PHONE 6',30000000.00,1,30000000.00),(21,20,31,'ASUS ROG PHONE 6',30000000.00,1,30000000.00),(22,21,12,'OPPO Reno 10',8990000.00,1,8990000.00),(26,23,13,'Samsung Galaxy S2',25000000.00,1,25000000.00),(28,25,34,'Samsung Galaxy S22 Plus (8GB - 256GB)',16990000.00,1,16990000.00),(29,1,34,'Samsung Galaxy S22 Plus (8GB - 256GB)',16990000.00,1,16990000.00),(30,1,33,'Samsung Galaxy S25 Ultra 12GB 256GB',28490000.00,1,28490000.00),(31,2,32,'Samsung Galaxy S24 8GB 256GB',22990000.00,1,22990000.00),(33,4,31,'ASUS ROG PHONE 6',30000000.00,1,30000000.00),(34,5,33,'Samsung Galaxy S25 Ultra 12GB 256GB',28490000.00,1,28490000.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int DEFAULT NULL,
  `customer_name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` text COLLATE utf8mb4_unicode_ci,
  `total_amount` double NOT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT 'PENDING',
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_customer` (`customer_id`),
  CONSTRAINT `fk_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE SET NULL,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,3,'Nguyễn Văn Toán','bleachfc20k@gmail.com','0394240586','Dong Phu, Xuan Phu, Yen Dung',45480000,'PENDING','2025-07-12 10:23:40'),(2,3,'Nguyễn Văn Toán','bleachfc20k@gmail.com','0394240586','Dong Phu, Xuan Phu, Yen Dung',22990000,'PENDING','2025-07-12 10:23:56'),(4,2,'Long Dai Ca','bleachfc2k@gmail.com','123456789','Ha Noi',30000000,'PENDING','2025-07-12 10:26:31'),(5,2,'Long Dai Ca','bleachfc2k@gmail.com','0394240586','Ha Noi',28490000,'PENDING','2025-07-12 10:53:12');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(15,2) NOT NULL,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `category_id` int DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `stock_quantity` int DEFAULT '0',
  `brand` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `specs` text COLLATE utf8mb4_unicode_ci,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idx_products_name` (`name`),
  KEY `idx_products_price` (`price`),
  KEY `idx_products_category` (`category_id`),
  KEY `idx_products_brand` (`brand`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'iPhone 15 Pro Max',29990000.00,'iphone15promax.jpg','2025-06-12 10:39:44','2025-06-12 12:45:01',1,'iPhone 15 Pro Max với thiết kế titan siêu nhẹ, chip A17 Pro mạnh mẽ và camera 48MP chuyên nghiệp.',77,'Apple','Màn hình: 6.7 inch OLED Super Retina XDR\nChip: Apple A17 Pro\nRAM: 8GB\nCamera: 48MP + 12MP + 12MP\nPin: 4422mAh\nSạc nhanh: 20W',1),(2,'Samsung Galaxy S24 Ultra',26990000.00,'galaxys24ultra.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',2,'Galaxy S24 Ultra sở hữu màn hình Dynamic AMOLED 2X, bút S-Pen tích hợp và camera 200MP cực khủng.',10,'Samsung','Màn hình: 6.8 inch QHD+ AMOLED\nChip: Snapdragon 8 Gen 3\nRAM: 12GB\nCamera: 200MP + 12MP + 10MP + 10MP\nPin: 5000mAh\nSạc nhanh: 45W',1),(3,'Xiaomi 14 Ultra',22990000.00,'xiaomi14ultra.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',3,'Xiaomi 14 Ultra là flagship mới nhất với cụm camera Leica cao cấp và hiệu năng mạnh mẽ.',15,'Xiaomi','Màn hình: 6.73 inch AMOLED 120Hz\nChip: Snapdragon 8 Gen 3\nRAM: 12GB\nCamera: 50MP Leica Quad\nPin: 5000mAh\nSạc nhanh: 90W',1),(4,'OnePlus 12',18990000.00,'oneplus12.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',6,'OnePlus 12 là lựa chọn hiệu năng cao giá hợp lý, camera Hasselblad, sạc siêu nhanh.',18,'OnePlus','Màn hình: 6.82 inch AMOLED QHD+\nChip: Snapdragon 8 Gen 3\nRAM: 16GB\nCamera: 50MP + 48MP + 64MP\nPin: 5400mAh\nSạc nhanh: 100W',1),(5,'Google Pixel 8 Pro',21990000.00,'pixel8pro.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',7,'Pixel 8 Pro với chip Tensor G3, camera AI mạnh mẽ và hỗ trợ cập nhật phần mềm dài hạn từ Google.',9,'Google','Màn hình: 6.7 inch OLED LTPO\nChip: Google Tensor G3\nRAM: 12GB\nCamera: 50MP + 48MP + 48MP\nPin: 5050mAh\nSạc nhanh: 30W',1),(6,'Oppo Find X7 Ultra',24990000.00,'oppofindx7ultra.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',4,'Flagship cao cấp của Oppo với camera xoay linh hoạt, màn hình sắc nét và sạc siêu nhanh.',14,'Oppo','Màn hình: 6.82 inch AMOLED LTPO\nChip: Snapdragon 8 Gen 3\nRAM: 16GB\nCamera: 50MP Quad Hasselblad\nPin: 5000mAh\nSạc nhanh: 100W',1),(7,'Vivo X100 Pro',23990000.00,'vivox100pro.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',5,'Vivo X100 Pro nổi bật với camera ZEISS, cấu hình mạnh và thiết kế sang trọng.',11,'Vivo','Màn hình: 6.78 inch AMOLED 120Hz\nChip: Dimensity 9300\nRAM: 16GB\nCamera: 50MP ZEISS Triple\nPin: 5400mAh\nSạc nhanh: 120W',1),(8,'Realme GT 5 Pro',15990000.00,'realmegtpro.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',8,'Flagship mạnh mẽ giá tốt đến từ Realme, hiệu năng cao, màn hình đẹp và sạc nhanh.',16,'Realme','Màn hình: 6.78 inch AMOLED 144Hz\nChip: Snapdragon 8 Gen 3\nRAM: 12GB\nCamera: 50MP + 8MP + 2MP\nPin: 5400mAh\nSạc nhanh: 100W',1),(9,'iPhone 14 Pro',29990000.00,'iphone14pro.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',1,'iPhone 14 Pro vẫn là lựa chọn tốt với hiệu năng mượt mà, camera chất lượng và Dynamic Island.',10,'Apple','Màn hình: 6.1 inch Super Retina XDR\nChip: Apple A16 Bionic\nRAM: 6GB\nCamera: 48MP + 12MP + 12MP\nPin: 3200mAh\nSạc nhanh: 20W',1),(10,'Samsung Galaxy S23',21990000.00,'samsung_s23.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',2,'Samsung Galaxy S23 có kích thước gọn, cấu hình tốt và camera sắc nét.',13,'Samsung','Màn hình: 6.1 inch AMOLED FHD+\nChip: Snapdragon 8 Gen 2\nRAM: 8GB\nCamera: 50MP + 12MP + 10MP\nPin: 3900mAh\nSạc nhanh: 25W',1),(11,'Xiaomi Redmi Note 12',6990000.00,'redmi_note12.jpg','2025-06-12 10:39:44','2025-06-12 10:41:54',3,'Redmi Note 12 là mẫu điện thoại tầm trung nổi bật với màn hình AMOLED và pin lớn.',30,'Xiaomi','Màn hình: 6.67 inch AMOLED 120Hz\nChip: Snapdragon 685\nRAM: 6GB\nCamera: 50MP + 8MP + 2MP\nPin: 5000mAh\nSạc nhanh: 33W',1),(12,'OPPO Reno 10',8990000.00,'oppo_reno10.jpg','2025-06-12 10:39:44','2025-06-12 12:49:50',4,'Oppo Reno 10 sở hữu thiết kế đẹp, camera chân dung chất lượng và hiệu năng ổn.',26,'Oppo','Màn hình: 6.7 inch AMOLED\nChip: Dimensity 7050\nRAM: 8GB\nCamera: 64MP + 32MP + 8MP\nPin: 5000mAh\nSạc nhanh: 67W',1),(13,'Samsung Galaxy S2',25000000.00,'1749729977956_samsung_galaxy_s2.jpg','2025-06-12 12:06:17','2025-06-21 12:27:40',2,'Samsung',9,'Samsung','Màn hình: 6.1 inch AMOLED FHD+',1),(31,'ASUS ROG PHONE 6',30000000.00,'1749734767851_asus_rog_phone.jpg','2025-06-12 13:26:07','2025-07-02 10:34:07',6,'ASUS',24,'Asus','ASUS',1),(32,'Samsung Galaxy S24 8GB 256GB',22990000.00,'1751452692318_samsungs24u.jpg','2025-07-02 10:38:12','2025-07-02 10:38:12',2,'Samsung Galaxy S24 256GB được trang bị bộ xử lý Exynos 2400 do Samsung tự sản xuất với 10 nhân CPU cùng bộ nhớ RAM 8GB, bộ nhớ trong 256GB. Màn hình thiết bị với kích thước 6.2 inch với khung viền siêu mỏng cùng công nghệ Dynamic AMOLED 2X. Phía sau điện thoại là cụm ba camera 50MP + 10MP + 12MP. Cùng với đó, Samsung S24 sở hữu nhiều tính năng AI hữu ích như dịch thuật trực tiếp, tìm kiếm bằng hình ảnh,...',50,'Samsung','Kích thước màn hình	\r\n6.2 inches\r\n\r\nCông nghệ màn hình	\r\nDynamic AMOLED 2X\r\n\r\nCamera sau	\r\nCamera chính 50 MP, f/1.8\r\nCamera tele: 10 MP, f/2.4, PDAF, OIS, zoom quang học 3x\r\nCamera góc siêu rộng: 12 MP, f/2.2, 120˚\r\n\r\nCamera trước	\r\n12 MP, f/2.2\r\n\r\nChipset	\r\nExynos 2400\r\n\r\nCông nghệ NFC	\r\nCó\r\n\r\nDung lượng RAM	\r\n8 GB\r\n\r\nBộ nhớ trong	\r\n256 GB\r\n\r\nPin	\r\n4000 mAh\r\n\r\nThẻ SIM	\r\nSIM 1 + SIM 2 / SIM 1 + eSIM / 2 eSIM\r\n\r\nHệ điều hành	\r\nAndroid 14, One UI 6.1\r\n\r\nĐộ phân giải màn hình	\r\n1080 x 2340 pixels (FullHD+)\r\n\r\nTính năng màn hình	\r\n120Hz, Độ sáng tối đa 2600 nits, Corning® Gorilla® Glass Victus® 2, 16 triệu màu\r\n\r\nLoại CPU	\r\n1 nhân 3.3 GHz, 5 nhân 3.2 GHz & 2 nhân 2.3 GHz\r\n\r\nTương thích	\r\nIP68',1),(33,'Samsung Galaxy S25 Ultra 12GB 256GB',28490000.00,'1751453019898_samsung-galaxy-s25-ultra.jpg','2025-07-02 10:43:39','2025-07-02 10:43:39',2,'Samsung S25 Ultra mạnh mẽ với chip Snapdragon 8 Elite For Galaxy mới nhất, RAM 12GB và bộ nhớ trong 256GB-1TB. Hệ thống 3 camera sau chất lượng gồm camera chính 200MP, camera tele 50MP và camera góc siêu rộng 50MP. Thiết kế kính cường lực Corning Gorilla Armor 2 và khung viền Titanium, màn hình Dynamic AMOLED 6.9 inch. Điện thoại này còn có viên pin 5000mAh, hỗ trợ 5G và Galaxy AI ấn tượng, nâng cao trải nghiệm người dùng!',40,'Samsung','Kích thước màn hình	\r\n6.9 inches\r\n\r\nCông nghệ màn hình	\r\nDynamic AMOLED 2X\r\n\r\nCamera sau	\r\nCamera siêu rộng 50MP\r\nCamera góc rộng 200 MP\r\nCamera Tele (5x) 50MP\r\nCamera Tele (3x) 10MP\"\r\n\r\nCamera trước	\r\n12 MP\r\n\r\nChipset	\r\nSnapdragon 8 Elite dành cho Galaxy (3nm)\r\n\r\nCông nghệ NFC	\r\nCó\r\n\r\nDung lượng RAM	\r\n12 GB\r\n\r\nBộ nhớ trong	\r\n256 GB\r\n\r\nPin	\r\n5000 mAh\r\n\r\nThẻ SIM	\r\n2 Nano-SIM + eSIM\r\n\r\nHệ điều hành	\r\nAndroid 15\r\n\r\nĐộ phân giải màn hình	\r\n3120 x 1440 pixels (Quad HD+)\r\n\r\nTính năng màn hình	\r\n120Hz\r\n2600 nits\r\nCorning® Gorilla® Armor 2',1),(34,'Samsung Galaxy S22 Plus (8GB - 256GB)',16990000.00,'1751453269782_samsungs22_1.jpg','2025-07-02 10:47:49','2025-07-02 10:47:49',2,'Samsung Galaxy S22 Plus (8GB - 256GB) là siêu phẩm mới nhất từ Samsung, mang đến hiệu năng mạnh mẽ với chip Snapdragon 8 Gen 2, màn hình Dynamic AMOLED 2X 6.6 inch sắc nét. Camera 50MP cải tiến giúp bạn chụp ảnh chuyên nghiệp, pin 4700mAh hỗ trợ sạc nhanh 45W đảm bảo sử dụng cả ngày. Thiết kế sang trọng, màu sắc thời thượng, S23 Plus là lựa chọn lý tưởng cho người yêu công nghệ.',35,'Samsung','Kích thước màn hình	\r\n6.6 inches\r\n\r\nCông nghệ màn hình	\r\nDynamic AMOLED 2X\r\n\r\nCamera sau	\r\nCamera góc rộng: 50 MP, f/1.8\r\nCamera góc siêu rộng: 12 MP, f/2.2\r\nCamera Tele: 10MP, f/2.4\r\n\r\nCamera trước	\r\n10 MP, f/2.2\r\n\r\nChipset	\r\nSnapdragon 8 Gen 1 8 nhân\r\n\r\nCông nghệ NFC	\r\nCó\r\n\r\nDung lượng RAM	\r\n8 GB\r\n\r\nBộ nhớ trong	\r\n256 GB\r\n\r\nPin	\r\n4500 mAh\r\n\r\nThẻ SIM	\r\n2 Nano SIM hoặc 1 Nano + 1 eSIM\r\n\r\nHệ điều hành	\r\nAndroid 12\r\n\r\nĐộ phân giải màn hình	\r\n1080 x 2340 pixels (FullHD+)\r\n\r\nTính năng màn hình	\r\nKính cường lực Corning Gorilla Glass Victus+\r\nĐộ sáng tối đa 1750 nits\r\nTần số quét 120 Hz\r\n\r\nLoại CPU	\r\n1 nhân 3 GHz, 3 nhân 2.5 GHz & 4 nhân 1.79 GHz',1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int NOT NULL,
  `customer_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `rating` int NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci,
  `review_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `approved` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_reviews_product` (`product_id`),
  KEY `idx_reviews_approved` (`approved`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `reviews_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-16 16:44:26
