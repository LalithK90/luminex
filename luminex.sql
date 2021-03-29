-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3308
-- Generation Time: Mar 30, 2021 at 12:07 AM
-- Server version: 8.0.18
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `etracker`
--
CREATE DATABASE IF NOT EXISTS `etracker` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `etracker`;

-- --------------------------------------------------------

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
CREATE TABLE IF NOT EXISTS `expenses` (
  `idexpenses` int(11) NOT NULL AUTO_INCREMENT,
  `expense_date` date DEFAULT NULL,
  `description` text,
  `payment` float DEFAULT NULL,
  `payment_type` varchar(10) DEFAULT NULL,
  `users_idusers` int(11) NOT NULL,
  PRIMARY KEY (`idexpenses`),
  KEY `fk_expenses_users_idx` (`users_idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `expenses`
--

INSERT INTO `expenses` (`idexpenses`, `expense_date`, `description`, `payment`, `payment_type`, `users_idusers`) VALUES
(1, '2020-12-03', 'Mouse', 500, 'Standard', 1),
(4, '2020-12-03', 'Rice', 500, 'Standard', 1);

-- --------------------------------------------------------

--
-- Table structure for table `expenses_has_regular_expenses`
--

DROP TABLE IF EXISTS `expenses_has_regular_expenses`;
CREATE TABLE IF NOT EXISTS `expenses_has_regular_expenses` (
  `expenses_idexpenses` int(11) NOT NULL,
  `regular_expenses_idregular_expenses` int(11) NOT NULL,
  PRIMARY KEY (`expenses_idexpenses`,`regular_expenses_idregular_expenses`),
  KEY `fk_expenses_has_regular_expenses_regular_expenses1_idx` (`regular_expenses_idregular_expenses`),
  KEY `fk_expenses_has_regular_expenses_expenses1_idx` (`expenses_idexpenses`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `prerequisites`
--

DROP TABLE IF EXISTS `prerequisites`;
CREATE TABLE IF NOT EXISTS `prerequisites` (
  `idprerequisites` int(11) NOT NULL AUTO_INCREMENT,
  `daily_rate` float DEFAULT NULL,
  `monthly_reservation` float DEFAULT NULL,
  `users_idusers` int(11) NOT NULL,
  PRIMARY KEY (`idprerequisites`),
  KEY `fk_prerequisites_users1_idx` (`users_idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `prerequisites`
--

INSERT INTO `prerequisites` (`idprerequisites`, `daily_rate`, `monthly_reservation`, `users_idusers`) VALUES
(1, 1500, 50000, 1);

-- --------------------------------------------------------

--
-- Table structure for table `regular_expenses`
--

DROP TABLE IF EXISTS `regular_expenses`;
CREATE TABLE IF NOT EXISTS `regular_expenses` (
  `idregular_expenses` int(11) NOT NULL AUTO_INCREMENT,
  `payment` float DEFAULT NULL,
  `description` text,
  `users_idusers` int(11) NOT NULL,
  PRIMARY KEY (`idregular_expenses`),
  KEY `fk_regular_expenses_users1_idx` (`users_idusers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `idusers` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idusers`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`idusers`, `first_name`, `last_name`, `email`, `password`) VALUES
(1, 'Tharika', 'Hansamali', 'tharika@gmail.com', '12345');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `expenses`
--
ALTER TABLE `expenses`
  ADD CONSTRAINT `fk_expenses_users` FOREIGN KEY (`users_idusers`) REFERENCES `users` (`idusers`);

--
-- Constraints for table `expenses_has_regular_expenses`
--
ALTER TABLE `expenses_has_regular_expenses`
  ADD CONSTRAINT `fk_expenses_has_regular_expenses_expenses1` FOREIGN KEY (`expenses_idexpenses`) REFERENCES `expenses` (`idexpenses`),
  ADD CONSTRAINT `fk_expenses_has_regular_expenses_regular_expenses1` FOREIGN KEY (`regular_expenses_idregular_expenses`) REFERENCES `regular_expenses` (`idregular_expenses`);

--
-- Constraints for table `prerequisites`
--
ALTER TABLE `prerequisites`
  ADD CONSTRAINT `fk_prerequisites_users1` FOREIGN KEY (`users_idusers`) REFERENCES `users` (`idusers`);

--
-- Constraints for table `regular_expenses`
--
ALTER TABLE `regular_expenses`
  ADD CONSTRAINT `fk_regular_expenses_users1` FOREIGN KEY (`users_idusers`) REFERENCES `users` (`idusers`);
--
-- Database: `fixit`
--
CREATE DATABASE IF NOT EXISTS `fixit` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `fixit`;

-- --------------------------------------------------------

--
-- Table structure for table `admin_login`
--

DROP TABLE IF EXISTS `admin_login`;
CREATE TABLE IF NOT EXISTS `admin_login` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin_login`
--

INSERT INTO `admin_login` (`id`, `username`, `password`) VALUES
(1, 'imantha', '123');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(150) NOT NULL,
  `product_price` int(5) NOT NULL,
  `product_qty` int(5) NOT NULL,
  `product_image` varchar(500) NOT NULL,
  `product_category` varchar(50) NOT NULL,
  `product_description` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `product_name`, `product_price`, `product_qty`, `product_image`, `product_category`, `product_description`) VALUES
(1, 'Nova Scotia', 250, 200, './product_image/cbcf79a070ba70c1695f481b39fa70caDIEP1-400x400.jpg', 'Elecrical', 'Home Energy Efficiency Product Installation.\r\n\r\n6 Months warranty.'),
(2, 'LED Ceiling Fan Bulb', 300, 320, './product_image/8a020dd3db828f78d493c82a8b667493white-feit-electric-led-bulbs-bpa1560n-950ca-2-64_400_compressed.jpg', 'Elecrical', '60W Equivalent A15 Intermediate Dimmable CEC title 90+ CRI White Glass.\r\n\r\n1 year Warranty.'),
(3, 'Smart Wares', 400, 400, './product_image/34972a222afdd8ea0ce8eefda0ef71dcsh4-99954fr_1.jpg', 'Elecrical', 'See all smart home basic products for home.'),
(4, 'Dual USB wall socket ', 130, 500, './product_image/2e7a275af9abd7ac1652b3a30d4371de1166175629.g_400-w_g.jpg', 'Elecrical', '2 Switch home power point supply plate AU Plug Dual USB Australian wall socket.\r\n\r\n6 months warranty.'),
(5, 'Light wave', 350, 220, './product_image/a37680ef321a2362bb08ae44e566b83aL22_Front_shadow_800x800_c28f0768-56fb-4573-a8e7-5d811fbc04c3_400x400_crop_center.webp', 'Elecrical', 'Smart Lights.\r\n1 year warranty. '),
(6, 'Wifi Smart switch timer', 220, 330, './product_image/0fe9fe72163f9b3059ad61825400cd14s-l400.jpg', 'Elecrical', '5x wifi smart switch timer IOS/Android APP remot control for home light socket.\r\n\r\n6 months warranty.'),
(7, '40A 3P MCB', 360, 420, './product_image/decd68228d03a5d648f13d2f3ef6059040A-Rated-Current-AC400V-3P-MCB-Miniature-Circuit-Breaker.jpg', 'Elecrical', '40A rated current AC400V 3P miniature circuit breaker.'),
(9, 'Main wire Roll', 570, 420, './product_image/27c44b8571e5d53e7d7cd36880e20e41GetImage.jpg', 'Elecrical', '100m Main wire roll.'),
(10, 'Earth wire', 435, 330, './product_image/40898ea9d7b87d06628498377eba1177centaur_gye_3-4_0c634bbceecfd779edc8abe78034793d.jpg', 'Elecrical', '100m 3mm PVC green/yellow earth wire roll.'),
(11, 'Insulated Tool Kit', 650, 80, './product_image/8b440d63d7dd5c7810c7665d6008c0c135-9108thumb.jpg', 'Elecrical', 'Ideal 35-9108 Insulated tool kit.\r\n9 Pieces.');
--
-- Database: `kl1db`
--
CREATE DATABASE IF NOT EXISTS `kl1db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `kl1db`;

-- --------------------------------------------------------

--
-- Table structure for table `customer_master`
--

DROP TABLE IF EXISTS `customer_master`;
CREATE TABLE IF NOT EXISTS `customer_master` (
  `customercode` int(11) NOT NULL AUTO_INCREMENT,
  `customername` varchar(100) NOT NULL,
  `customeraddress` text NOT NULL,
  `mobilenumber` int(10) NOT NULL,
  `emailaddress` varchar(50) NOT NULL,
  PRIMARY KEY (`customercode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_master`
--

DROP TABLE IF EXISTS `order_master`;
CREATE TABLE IF NOT EXISTS `order_master` (
  `orderid` int(5) NOT NULL AUTO_INCREMENT,
  `itemcode` varchar(20) NOT NULL,
  `itemname` varchar(60) DEFAULT NULL,
  `itemdescription` varchar(100) NOT NULL,
  `qty` int(11) NOT NULL,
  `orderdate` date NOT NULL,
  `amountUSD` point NOT NULL,
  `customercode` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `customer_master`
--
ALTER TABLE `customer_master`
  ADD CONSTRAINT `customer_master_ibfk_1` FOREIGN KEY (`customercode`) REFERENCES `order_master` (`orderid`) ON DELETE CASCADE ON UPDATE CASCADE;
--
-- Database: `luminex`
--
CREATE DATABASE IF NOT EXISTS `luminex` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `luminex`;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `live_dead` varchar(255) DEFAULT NULL,
  `main_category` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `live_dead`, `main_category`, `name`) VALUES
(1, 'ACTIVE', 'CABLE', 'Kelani Cable '),
(2, 'ACTIVE', 'CABLE', 'Orange Cable'),
(3, 'ACTIVE', 'CABLE', 'Sierra Cable'),
(4, 'ACTIVE', 'CEMENT', 'Ambuja Cement'),
(5, 'ACTIVE', 'CEMENT', 'INSEE Cement'),
(6, 'ACTIVE', 'CEMENT', 'Sanstha Cement'),
(7, 'ACTIVE', 'BUILDING_MATERIALS', 'Sand'),
(8, 'ACTIVE', 'HELMET', 'Red '),
(9, 'ACTIVE', 'HELMET', 'White'),
(10, 'ACTIVE', 'LADDER', 'Single'),
(11, 'ACTIVE', 'LADDER', 'Double'),
(12, 'ACTIVE', 'ROPE', 'Braided');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `calling_name` varchar(255) DEFAULT NULL,
  `civil_status` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `date_of_assignment` date DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `designation` varchar(255) DEFAULT NULL,
  `employee_status` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `land` varchar(10) DEFAULT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `mobile_one` varchar(10) DEFAULT NULL,
  `mobile_two` varchar(10) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nic` varchar(12) DEFAULT NULL,
  `office_email` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nbyivu8qgmx0r7wtbplf01gf8` (`code`),
  UNIQUE KEY `UK_dihajhqd7lkqn3lhsawly3t9r` (`nic`),
  UNIQUE KEY `UK_ldqrk7j96ef2tqxsonr4dqf0r` (`office_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee_files`
--

DROP TABLE IF EXISTS `employee_files`;
CREATE TABLE IF NOT EXISTS `employee_files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `mime_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `new_id` varchar(255) DEFAULT NULL,
  `new_name` varchar(255) DEFAULT NULL,
  `pic` longblob,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5eob6je5op1e4m7v20v5i01p2` (`new_id`),
  KEY `FKolualpa5dydj5r06txltc1y6d` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `good_received_note`
--

DROP TABLE IF EXISTS `good_received_note`;
CREATE TABLE IF NOT EXISTS `good_received_note` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `good_received_note_state` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `total_amount` decimal(10,2) DEFAULT NULL,
  `purchase_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl2ij2erc8mi59decwkp0fj4b4` (`purchase_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `good_received_note`
--

INSERT INTO `good_received_note` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `good_received_note_state`, `remarks`, `total_amount`, `purchase_order_id`) VALUES
(1, '2021-03-29 15:45:40.737290', 'anonymousUser', '2021-03-29 15:45:40.737290', 'anonymousUser', 'NOT_PAID', NULL, '219000.00', 1),
(2, '2021-03-29 16:40:26.733774', 'anonymousUser', '2021-03-29 16:40:26.733774', 'anonymousUser', 'NOT_PAID', NULL, '220000.00', 2);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `item_status` varchar(255) DEFAULT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `rop` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6cgogdarkq48dlg1lbnv4q1oq` (`code`),
  KEY `FK2n9w8d0dp4bsfra9dcg0046l4` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `code`, `item_status`, `live_dead`, `name`, `rop`, `category_id`) VALUES
(1, '2021-03-29 14:28:48.383150', 'anonymousUser', '2021-03-29 14:28:48.383150', 'anonymousUser', 'SSMI210000', 'JUSTENTERED', 'ACTIVE', '1.5mm cable 500m bundle', '20', 1),
(2, '2021-03-29 14:29:47.707129', 'anonymousUser', '2021-03-29 14:29:47.707129', 'anonymousUser', 'SSMI210001', NULL, 'ACTIVE', '1.5mm cable 500m bundle', '25', 2),
(3, '2021-03-29 14:31:11.039421', 'anonymousUser', '2021-03-29 14:31:46.029627', 'anonymousUser', NULL, NULL, 'ACTIVE', '30 Kg', '10', 4),
(4, '2021-03-29 14:31:32.291711', 'anonymousUser', '2021-03-29 14:31:32.291711', 'anonymousUser', 'SSMI210003', NULL, 'ACTIVE', '50 Kg', '20', 6),
(5, '2021-03-29 14:32:06.587243', 'anonymousUser', '2021-03-29 14:32:06.587243', 'anonymousUser', 'SSMI210004', NULL, 'ACTIVE', 'braided rope 1000m', '5', 12),
(6, '2021-03-29 14:32:38.560619', 'anonymousUser', '2021-03-29 14:32:38.560619', 'anonymousUser', 'SSMI210005', NULL, 'ACTIVE', 'braided rope 500m', '5', 12);

-- --------------------------------------------------------

--
-- Table structure for table `ledger`
--

DROP TABLE IF EXISTS `ledger`;
CREATE TABLE IF NOT EXISTS `ledger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `quantity` varchar(255) DEFAULT NULL,
  `good_received_note_id` int(11) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcykmvuwc8wn9n91rknufwli25` (`good_received_note_id`),
  KEY `FKe8g4swnkywn8be1ut2j8e8x3w` (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ledger`
--

INSERT INTO `ledger` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `live_dead`, `quantity`, `good_received_note_id`, `item_id`) VALUES
(1, '2021-03-29 15:45:40.783847', 'anonymousUser', '2021-03-29 15:45:40.783847', 'anonymousUser', 'ACTIVE', '22', 1, 2),
(2, '2021-03-29 15:45:40.800801', 'anonymousUser', '2021-03-29 15:45:40.800801', 'anonymousUser', 'ACTIVE', '21', 1, 5),
(3, '2021-03-29 15:45:40.803759', 'anonymousUser', '2021-03-29 16:40:26.686492', 'anonymousUser', 'ACTIVE', '210', 1, 3),
(4, '2021-03-29 16:40:26.736348', 'anonymousUser', '2021-03-29 16:40:26.736348', 'anonymousUser', 'ACTIVE', '25', 2, 1),
(5, '2021-03-29 16:40:26.738344', 'anonymousUser', '2021-03-29 16:40:26.738344', 'anonymousUser', 'ACTIVE', '10', 2, 3);

-- --------------------------------------------------------

--
-- Table structure for table `order_ledger`
--

DROP TABLE IF EXISTS `order_ledger`;
CREATE TABLE IF NOT EXISTS `order_ledger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `quantity` varchar(255) NOT NULL,
  `ledger_id` int(11) DEFAULT NULL,
  `project_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6bfepx01sxaijcqi1jypbelt7` (`ledger_id`),
  KEY `FKlpicqgj5d3yx8jna9cba3geig` (`project_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `bank_name` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `purchase_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_opor0kv54jt58n364jog9bu2i` (`code`),
  KEY `FK66h63qkt7xfa0xiwv4xgel9na` (`purchase_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `amount`, `bank_name`, `code`, `live_dead`, `payment_method`, `remarks`, `purchase_order_id`) VALUES
(1, '2021-03-29 15:46:12.426538', 'anonymousUser', '2021-03-29 15:46:12.426538', 'anonymousUser', '10023.00', '', 'SSMP210000', 'ACTIVE', 'CASH', '', 1),
(2, '2021-03-29 16:28:12.434245', 'anonymousUser', '2021-03-29 16:28:12.434245', 'anonymousUser', '10000.00', 'Bank of Ceylon', 'SSMP210001', 'ACTIVE', 'CREDIT', '', 1),
(3, '2021-03-29 16:28:49.792426', 'anonymousUser', '2021-03-29 16:28:49.792426', 'anonymousUser', '219000.00', 'Bank of Ceylon', 'SSMP210002', 'ACTIVE', 'CREDIT', '', 1),
(4, '2021-03-29 16:41:32.442146', 'anonymousUser', '2021-03-29 16:41:32.442146', 'anonymousUser', '120.00', '', 'SSMP210003', 'ACTIVE', 'CREDIT', '', 2);

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `project_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eh3nusutt0qy84a4yr9pfxkyg` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `project_employee`
--

DROP TABLE IF EXISTS `project_employee`;
CREATE TABLE IF NOT EXISTS `project_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `project_employee_status` varchar(255) DEFAULT NULL,
  `employee_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn5yqs0xm3rmsg62n84ccyk4k0` (`employee_id`),
  KEY `FK1907nkisp2dlsswuycpnakiv8` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `project_order`
--

DROP TABLE IF EXISTS `project_order`;
CREATE TABLE IF NOT EXISTS `project_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `order_state` varchar(255) DEFAULT NULL,
  `remarks` varchar(255) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ebyhndb7ojpfi2rf5m3mms247` (`code`),
  KEY `FK8hvr4qd86d1m5x8ak7swchtjx` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `purchase_order`
--

DROP TABLE IF EXISTS `purchase_order`;
CREATE TABLE IF NOT EXISTS `purchase_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `code` varchar(255) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `purchase_order_priority` varchar(255) DEFAULT NULL,
  `purchase_order_status` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lyhuui3e3rh2a6itktx3rwrpe` (`code`),
  KEY `FK4traogu3jriq9u7e8rvm86k7i` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `purchase_order`
--

INSERT INTO `purchase_order` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `code`, `live_dead`, `price`, `purchase_order_priority`, `purchase_order_status`, `remark`, `supplier_id`) VALUES
(1, '2021-03-29 15:42:02.315766', 'anonymousUser', '2021-03-29 15:45:40.665954', 'anonymousUser', 'SSPO210000', 'ACTIVE', '219000.00', 'MEDIUM', 'NOT_PROCEED', '', 1),
(2, '2021-03-29 16:25:09.105690', 'anonymousUser', '2021-03-29 16:40:26.687489', 'anonymousUser', 'SSPO210001', 'ACTIVE', '220000.00', 'NORMAL', 'NOT_PROCEED', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `purchase_order_item`
--

DROP TABLE IF EXISTS `purchase_order_item`;
CREATE TABLE IF NOT EXISTS `purchase_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `buying_price` decimal(10,2) NOT NULL,
  `line_total` decimal(10,2) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `quantity` varchar(255) NOT NULL,
  `item_id` int(11) DEFAULT NULL,
  `purchase_order_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6hnf77cwyb21thxqtphoatybv` (`item_id`),
  KEY `FKmj122necubadvuquvjoq967y7` (`purchase_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `purchase_order_item`
--

INSERT INTO `purchase_order_item` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `buying_price`, `line_total`, `live_dead`, `quantity`, `item_id`, `purchase_order_id`) VALUES
(1, '2021-03-29 15:42:02.340673', 'anonymousUser', '2021-03-29 15:42:02.340673', 'anonymousUser', '2500.00', '55000.00', NULL, '22', 2, 1),
(2, '2021-03-29 15:42:02.346653', 'anonymousUser', '2021-03-29 15:42:02.346653', 'anonymousUser', '7000.00', '147000.00', NULL, '21', 5, 1),
(3, '2021-03-29 15:42:02.348646', 'anonymousUser', '2021-03-29 15:42:02.348646', 'anonymousUser', '8500.00', '17000.00', NULL, '2', 3, 1),
(4, '2021-03-29 16:25:09.108468', 'anonymousUser', '2021-03-29 16:25:09.108468', 'anonymousUser', '5400.00', '135000.00', NULL, '25', 1, 2),
(5, '2021-03-29 16:25:09.111458', 'anonymousUser', '2021-03-29 16:25:09.111458', 'anonymousUser', '8500.00', '85000.00', NULL, '10', 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_iubw515ff0ugtm28p8g3myt0h` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
CREATE TABLE IF NOT EXISTS `supplier` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `brn` varchar(255) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `contact_one` varchar(10) DEFAULT NULL,
  `contact_two` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `item_supplier_status` varchar(255) DEFAULT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_u0lh6hby20ok7au7646wrewl` (`code`),
  UNIQUE KEY `UK_g7qiwwu4vpciysmeeyme9gg1d` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `address`, `brn`, `code`, `contact_one`, `contact_two`, `email`, `item_supplier_status`, `live_dead`, `name`) VALUES
(1, '2021-03-29 13:57:04.298146', 'anonymousUser', '2021-03-29 14:02:49.016897', 'anonymousUser', 'Gampaha', 'WP2356', 'SSCS210000', '0115896312', '0714656896', 'sksupplier@gmail.com', NULL, 'ACTIVE', 'SK Suppliers'),
(2, '2021-03-29 13:57:58.000378', 'anonymousUser', '2021-03-29 13:57:58.000378', 'anonymousUser', 'Negambo', 'WS8956', 'SSCS210001', '0312569875', '0789685456', 'rkhardware@gmail.com', 'CURRENTLY_BUYING', 'ACTIVE', 'RK Hardware'),
(3, '2021-03-29 14:01:46.379650', 'anonymousUser', '2021-03-29 14:01:46.379650', 'anonymousUser', 'Kelaniya', 'WP1236', 'SSCS210002', '0115869478', '0778956123', 'kelanicablesuppiers@gmail.com', 'CURRENTLY_BUYING', 'ACTIVE', 'Kelani Cable Suppliers');

-- --------------------------------------------------------

--
-- Table structure for table `supplier_item`
--

DROP TABLE IF EXISTS `supplier_item`;
CREATE TABLE IF NOT EXISTS `supplier_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `item_supplier_status` varchar(255) DEFAULT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `item_id` int(11) DEFAULT NULL,
  `supplier_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK62ijb79q9ef3djeen9r48pal8` (`item_id`),
  KEY `FKlyx7pcth25eowl5u49n4ayos0` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `supplier_item`
--

INSERT INTO `supplier_item` (`id`, `created_at`, `created_by`, `updated_at`, `updated_by`, `item_supplier_status`, `live_dead`, `price`, `item_id`, `supplier_id`) VALUES
(1, '2021-03-29 14:34:09.442314', 'anonymousUser', '2021-03-29 15:07:19.381826', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '2500.00', 2, 1),
(2, '2021-03-29 14:35:39.752889', 'anonymousUser', '2021-03-29 15:07:19.431327', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '8500.00', 3, 1),
(3, '2021-03-29 14:35:39.844909', 'anonymousUser', '2021-03-29 15:07:19.467405', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '7000.00', 5, 1),
(4, '2021-03-29 14:35:39.910757', 'anonymousUser', '2021-03-29 15:07:19.521053', 'anonymousUser', 'STOPPED', NULL, '6500.00', 6, 1),
(5, '2021-03-29 15:07:35.532770', 'anonymousUser', '2021-03-29 15:08:34.407373', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '5200.00', 1, 2),
(6, '2021-03-29 15:08:51.459938', 'anonymousUser', '2021-03-29 15:08:51.459938', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '2580.00', 5, 2),
(7, '2021-03-29 15:08:51.719306', 'anonymousUser', '2021-03-29 15:08:51.719306', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '1200.00', 4, 2),
(8, '2021-03-29 15:09:09.962675', 'anonymousUser', '2021-03-29 15:09:09.962675', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '5800.00', 4, 3),
(9, '2021-03-29 15:09:10.029794', 'anonymousUser', '2021-03-29 15:09:10.029794', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '3620.00', 6, 3),
(10, '2021-03-29 16:18:19.586199', 'anonymousUser', '2021-03-29 16:18:19.586199', 'anonymousUser', 'CURRENTLY_BUYING', NULL, '5400.00', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `created_by` varchar(255) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `updated_by` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `live_dead` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `employee_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FK211dk0pe7l3aibwce8yy61ota` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_session_log`
--

DROP TABLE IF EXISTS `user_session_log`;
CREATE TABLE IF NOT EXISTS `user_session_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `failure_attempts` int(11) NOT NULL,
  `user_session_log_status` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrhb4wune1hnnhdsbiah2ojo5l` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employee_files`
--
ALTER TABLE `employee_files`
  ADD CONSTRAINT `FKolualpa5dydj5r06txltc1y6d` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Constraints for table `good_received_note`
--
ALTER TABLE `good_received_note`
  ADD CONSTRAINT `FKl2ij2erc8mi59decwkp0fj4b4` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`);

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `FK2n9w8d0dp4bsfra9dcg0046l4` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

--
-- Constraints for table `ledger`
--
ALTER TABLE `ledger`
  ADD CONSTRAINT `FKcykmvuwc8wn9n91rknufwli25` FOREIGN KEY (`good_received_note_id`) REFERENCES `good_received_note` (`id`),
  ADD CONSTRAINT `FKe8g4swnkywn8be1ut2j8e8x3w` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

--
-- Constraints for table `order_ledger`
--
ALTER TABLE `order_ledger`
  ADD CONSTRAINT `FK6bfepx01sxaijcqi1jypbelt7` FOREIGN KEY (`ledger_id`) REFERENCES `ledger` (`id`),
  ADD CONSTRAINT `FKlpicqgj5d3yx8jna9cba3geig` FOREIGN KEY (`project_order_id`) REFERENCES `project_order` (`id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `FK66h63qkt7xfa0xiwv4xgel9na` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`);

--
-- Constraints for table `project_employee`
--
ALTER TABLE `project_employee`
  ADD CONSTRAINT `FK1907nkisp2dlsswuycpnakiv8` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  ADD CONSTRAINT `FKn5yqs0xm3rmsg62n84ccyk4k0` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Constraints for table `project_order`
--
ALTER TABLE `project_order`
  ADD CONSTRAINT `FK8hvr4qd86d1m5x8ak7swchtjx` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`);

--
-- Constraints for table `purchase_order`
--
ALTER TABLE `purchase_order`
  ADD CONSTRAINT `FK4traogu3jriq9u7e8rvm86k7i` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`);

--
-- Constraints for table `purchase_order_item`
--
ALTER TABLE `purchase_order_item`
  ADD CONSTRAINT `FK6hnf77cwyb21thxqtphoatybv` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  ADD CONSTRAINT `FKmj122necubadvuquvjoq967y7` FOREIGN KEY (`purchase_order_id`) REFERENCES `purchase_order` (`id`);

--
-- Constraints for table `supplier_item`
--
ALTER TABLE `supplier_item`
  ADD CONSTRAINT `FK62ijb79q9ef3djeen9r48pal8` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`),
  ADD CONSTRAINT `FKlyx7pcth25eowl5u49n4ayos0` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK211dk0pe7l3aibwce8yy61ota` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `user_session_log`
--
ALTER TABLE `user_session_log`
  ADD CONSTRAINT `FKrhb4wune1hnnhdsbiah2ojo5l` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
--
-- Database: `service`
--
CREATE DATABASE IF NOT EXISTS `service` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `service`;

-- --------------------------------------------------------

--
-- Table structure for table `attendence`
--

DROP TABLE IF EXISTS `attendence`;
CREATE TABLE IF NOT EXISTS `attendence` (
  `idattendence` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `in_time` varchar(45) DEFAULT NULL,
  `out_time` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `day` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `employee_nic` varchar(45) NOT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idattendence`),
  KEY `fk_attendence_employee1` (`employee_nic`),
  KEY `fk_attendence_user1_idx` (`user_userid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `attendence`
--

INSERT INTO `attendence` (`idattendence`, `month`, `year`, `in_time`, `out_time`, `status`, `day`, `date`, `employee_nic`, `user_userid`) VALUES
(1, 'Jul', '2019', '11:57:22', '12:00:18', 'off', 'Sat', '20', '123', 6),
(2, 'Jul', '2019', '06:10:14', '', 'work', 'Mon', '22', '123', 6),
(3, 'Jul', '2019', '12:36:02', '12:36:21', 'off', 'Mon', '22', '199819601510', 6);

-- --------------------------------------------------------

--
-- Table structure for table `bonus`
--

DROP TABLE IF EXISTS `bonus`;
CREATE TABLE IF NOT EXISTS `bonus` (
  `idbonus` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(3) DEFAULT NULL,
  `bonus_price` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idbonus`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bonus`
--

INSERT INTO `bonus` (`idbonus`, `month`, `bonus_price`, `description`, `status`) VALUES
(1, 'Jul', 1000, 'Monthly Attendance Allowance', 'Active'),
(2, 'Aug', 1000, 'Monthly Attendance Allowance', 'Active'),
(3, 'Jul', 1000, 'savw', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `booking_id` varchar(20) NOT NULL,
  `vehicle_no` varchar(45) DEFAULT NULL,
  `mobile_no` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` varchar(25) DEFAULT NULL,
  `status` varchar(12) DEFAULT NULL,
  `service_id` int(11) NOT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `fk_booking_service_type1_idx` (`service_id`),
  KEY `fk_booking_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`booking_id`, `vehicle_no`, `mobile_no`, `date`, `time`, `status`, `service_id`, `user_userid`) VALUES
('B1', '45', 55, '2019-07-12', '9:00', 'Used', 1, 1),
('B2', '525', 123, '2019-07-12', '9:30', 'Used', 1, 1),
('B3', 'WU-4585', 778452122, '2019-07-25', '14:00', 'Active', 3, 6);

-- --------------------------------------------------------

--
-- Table structure for table `cash_payment`
--

DROP TABLE IF EXISTS `cash_payment`;
CREATE TABLE IF NOT EXISTS `cash_payment` (
  `idcash_payment` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `grn_idgrn` varchar(45) NOT NULL,
  `suppliers_idsuppliers` varchar(45) NOT NULL,
  PRIMARY KEY (`idcash_payment`),
  KEY `fk_cash_payment_grn1` (`grn_idgrn`),
  KEY `fk_cash_payment_suppliers1` (`suppliers_idsuppliers`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cash_payment`
--

INSERT INTO `cash_payment` (`idcash_payment`, `amount`, `date`, `time`, `grn_idgrn`, `suppliers_idsuppliers`) VALUES
(1, 13300, '2019-07-22', '03:51:25', 'grn0001', 'S001'),
(2, 12825, '2019-07-22', '03:56:00', 'grn0002', 'S001'),
(3, 4750, '2019-07-22', '09:24:34', 'grn0003', 'S001'),
(4, 5700, '2019-07-22', '12:30:49', 'grn0004', 'S001');

-- --------------------------------------------------------

--
-- Table structure for table `check_payment`
--

DROP TABLE IF EXISTS `check_payment`;
CREATE TABLE IF NOT EXISTS `check_payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `deposite_date` date DEFAULT NULL,
  `cheq_no` varchar(45) DEFAULT NULL,
  `grn_idgrn` varchar(45) NOT NULL,
  `suppliers_idsuppliers` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_check_payment_grn1` (`grn_idgrn`),
  KEY `fk_check_payment_suppliers1` (`suppliers_idsuppliers`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer` (
  `cus_nic` varchar(45) NOT NULL,
  `cus_name` varchar(45) DEFAULT NULL,
  `mobile` int(11) DEFAULT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`cus_nic`),
  KEY `fk_customer_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cus_nic`, `cus_name`, `mobile`, `user_userid`) VALUES
('1', 'sarani', 11, 1),
('2', 'dad', 22, 1),
('3', 'jm', 33, 1),
('4', 'nn', 44, 1),
('931234567v', 'Shiyan Sanka', 777915535, 1),
('981961510v', 'Hasun Nilupul', 783462499, 1);

-- --------------------------------------------------------

--
-- Table structure for table `customers_vehicles`
--

DROP TABLE IF EXISTS `customers_vehicles`;
CREATE TABLE IF NOT EXISTS `customers_vehicles` (
  `owner_vehicles` int(11) NOT NULL AUTO_INCREMENT,
  `v_no` varchar(45) DEFAULT NULL,
  `new_milage` double DEFAULT NULL,
  `cus_nic` varchar(45) NOT NULL,
  `v_type` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`owner_vehicles`),
  KEY `fk_vehicles_customer1` (`cus_nic`),
  KEY `fk_customers_vehicles_vehicle_type1` (`v_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers_vehicles`
--

INSERT INTO `customers_vehicles` (`owner_vehicles`, `v_no`, `new_milage`, `cus_nic`, `v_type`, `date`) VALUES
(1, 'GH-7685', 3000, '981961510v', 'car', '2019-07-19'),
(2, 'CAB-7845', 800, '931234567v', 'car', '2019-07-22');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `nic` varchar(45) NOT NULL,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `imagelink` varchar(255) DEFAULT NULL,
  `employee_salary_salaryid` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  `image` varchar(450) DEFAULT NULL,
  `date1` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`nic`),
  KEY `fk_employee_employee_salary1` (`employee_salary_salaryid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`nic`, `firstname`, `lastname`, `address`, `gender`, `dob`, `mobile`, `imagelink`, `employee_salary_salaryid`, `status`, `image`, `date1`) VALUES
('1222', 'sarani', 'Sarangi', 'jhjkk', 'Male', '1997-05-05', '0112255', 'C:/Users/Hasun Nilupul/Pictures/icons/Today_30px.png', '1', 'deactive', 'E:/photo/My/BeautyPlusMe_20180730152122_fast.jpg', '02052019'),
('123', 'Nethin', 'Sandaru', 'eejj', 'Female', '0199-05-03', '7896', 'C:/Users/Hasun Nilupul/Pictures/icons/Today_30px.png', '1', 'deactive', 'jljl555', '03022018'),
('199819601510', 'Hasun', 'Nilupul', 'Meerigama', 'male', '1998-07-12', '783462499', 'C:/Users/Hasun Nilupul/Pictures/icons/land-sales.png', '1', 'Active', 'JPEG', '1998 07 12'),
('941842364v', 'Madhura', 'Madhusanka', 'Meerigama', 'male', '1994-07-02', '774758658', 'C:/Users/Hasun Nilupul/Pictures/icons/Invoice_30px.png', '1', 'Active', 'JPEG', '1994 07 02'),
('981961510V', 'Hasun', 'Nilupul', '78/20 Mahagedarawatta,Alutepola,Wegowwa', 'male', '1998-07-12', '784728616', 'C:/Users/Hasun Nilupul/Pictures/icons/Today_30px.png', '1', 'deactive', 'JPEG', '1998 07 12');

-- --------------------------------------------------------

--
-- Table structure for table `employee_salary`
--

DROP TABLE IF EXISTS `employee_salary`;
CREATE TABLE IF NOT EXISTS `employee_salary` (
  `salaryid` varchar(45) NOT NULL,
  `basic_salary` double DEFAULT NULL,
  `emp_type` int(11) NOT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`salaryid`),
  KEY `fk_employee_salary_emp_type1_idx` (`emp_type`),
  KEY `fk_employee_salary_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_salary`
--

INSERT INTO `employee_salary` (`salaryid`, `basic_salary`, `emp_type`, `user_userid`) VALUES
('1', 15000, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `emp_leave`
--

DROP TABLE IF EXISTS `emp_leave`;
CREATE TABLE IF NOT EXISTS `emp_leave` (
  `idleave` int(11) NOT NULL AUTO_INCREMENT,
  `year` varchar(45) DEFAULT NULL,
  `month` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `date` varchar(3) DEFAULT NULL,
  `employee_nic` varchar(45) NOT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idleave`),
  KEY `fk_leave_employee1_idx` (`employee_nic`),
  KEY `fk_leave_user1_idx` (`user_userid`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emp_leave`
--

INSERT INTO `emp_leave` (`idleave`, `year`, `month`, `type`, `reason`, `date`, `employee_nic`, `user_userid`) VALUES
(1, NULL, NULL, NULL, 'illness', NULL, '1222', 1),
(2, NULL, NULL, NULL, 'illness', NULL, '1222', 1),
(3, NULL, NULL, NULL, 'illness', NULL, '1222', 1),
(4, NULL, NULL, NULL, 'fafa', NULL, '123', 1),
(5, NULL, NULL, NULL, 'gj', NULL, '1222', 1),
(6, NULL, NULL, NULL, 'hkh', NULL, '1222', 1),
(7, NULL, NULL, NULL, 'fa', NULL, '1222', 1),
(8, NULL, NULL, NULL, 'faf', NULL, '1222', 1),
(9, NULL, NULL, NULL, 'fjgjg', NULL, '123', 1),
(10, NULL, NULL, NULL, 'yky', NULL, '123', 1),
(11, NULL, NULL, NULL, 'gky', NULL, '123', 1),
(12, '2019', 'Jul', NULL, 'normal', '22', '123', 6),
(13, '2019', 'Jul', 'normal', 'Sick', '22', '123', 6),
(14, '2019', 'Jul', 'normal', 'Migraine', '22', '981961510V', 6);

-- --------------------------------------------------------

--
-- Table structure for table `emp_type`
--

DROP TABLE IF EXISTS `emp_type`;
CREATE TABLE IF NOT EXISTS `emp_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `emp_type`
--

INSERT INTO `emp_type` (`id`, `emp_type`) VALUES
(1, 'cleaner'),
(2, 'Director');

-- --------------------------------------------------------

--
-- Table structure for table `grn`
--

DROP TABLE IF EXISTS `grn`;
CREATE TABLE IF NOT EXISTS `grn` (
  `idgrn` varchar(45) NOT NULL,
  `amount` double DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `payment_type` varchar(45) DEFAULT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idgrn`),
  KEY `fk_grn_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grn`
--

INSERT INTO `grn` (`idgrn`, `amount`, `discount`, `date`, `time`, `status`, `payment_type`, `user_userid`) VALUES
('grn0001', 13300, 5, '2019-07-22', '03:51:25 AM', '', 'Cash', 6),
('grn0002', 12825, 5, '2019-07-22', '03:56:00 AM', '', 'Cash', 6),
('grn0003', 4750, 5, '2019-07-22', '09:24:34 AM', '', 'Cash', 6),
('grn0004', 5700, 5, '2019-07-22', '12:30:49 PM', '', 'Cash', 6);

-- --------------------------------------------------------

--
-- Table structure for table `grn_item`
--

DROP TABLE IF EXISTS `grn_item`;
CREATE TABLE IF NOT EXISTS `grn_item` (
  `idgrn_item` int(11) NOT NULL AUTO_INCREMENT,
  `buyingunitprice` double DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `discount` varchar(45) DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `total_items_value` double DEFAULT NULL,
  `grn_idgrn` varchar(45) NOT NULL,
  `item_iditem` varchar(45) NOT NULL,
  `sellingunitprice` double DEFAULT NULL,
  PRIMARY KEY (`idgrn_item`),
  KEY `fk_grn_item_grn1` (`grn_idgrn`),
  KEY `fk_grn_item_item1` (`item_iditem`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `grn_item`
--

INSERT INTO `grn_item` (`idgrn_item`, `buyingunitprice`, `date`, `time`, `discount`, `qty`, `total_items_value`, `grn_idgrn`, `item_iditem`, `sellingunitprice`) VALUES
(1, 800, '2019-07-22', '03:51:25 AM', '0', 10, 8000, 'grn0001', '3', 1000),
(2, 1200, '2019-07-22', '03:51:25 AM', '0', 5, 6000, 'grn0001', '3', 1800),
(3, 2700, '2019-07-22', '03:56:00 AM', '0', 5, 13500, 'grn0002', '4', 3500),
(4, 1000, '2019-07-22', '09:24:34 AM', '0', 5, 5000, 'grn0003', '3', 1500),
(5, 1000, '2019-07-22', '12:30:49 PM', '0', 1, 1000, 'grn0004', '3', 1500),
(6, 1000, '2019-07-22', '12:30:49 PM', '0', 5, 5000, 'grn0004', '2', 1500);

-- --------------------------------------------------------

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
CREATE TABLE IF NOT EXISTS `invoice` (
  `idinvoice` varchar(45) NOT NULL,
  `vehicle_no` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `mileage` double DEFAULT NULL,
  `payment` double DEFAULT NULL,
  `service_type_service_id` int(11) NOT NULL,
  `customer_cus_nic` varchar(45) NOT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idinvoice`),
  KEY `fk_invoice_service_type1` (`service_type_service_id`),
  KEY `fk_invoice_customer1_idx` (`customer_cus_nic`),
  KEY `fk_invoice_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice`
--

INSERT INTO `invoice` (`idinvoice`, `vehicle_no`, `date`, `time`, `mileage`, `payment`, `service_type_service_id`, `customer_cus_nic`, `user_userid`) VALUES
('1', '12', '2019-05-02', '12:02:05', 500, 2000, 1, '1', 1),
('10', '89', '2019-05-08', '12:08:03', 600, 800, 1, '4', 1),
('11', '89', '2019-05-08', '12:08:03', 600, 800, 1, '4', 1),
('2', '23', '2019-05-02', '12:02.05', 500, 200, 1, '1', 1),
('3', '45', '2019-07-12', '12:08:03', 400, 500, 1, '1', 1),
('4', '67', '2019-05-08', '12:08:03', 600, 800, 1, '1', 1),
('5', '89', '2019-05-08', '12:08:03', 600, 800, 1, '2', 1),
('6', '89', '2019-05-08', '12:08:03', 600, 800, 1, '2', 1),
('7', '89', '2019-05-08', '12:08:03', 600, 800, 1, '2', 1),
('8', '89', '2019-05-08', '12:08:03', 600, 800, 1, '3', 1),
('9', '89', '2019-07-12', '12:08:03', 600, 800, 1, '3', 1),
('inv00012', 'GH-7685', '2019-07-19', '07:06:58 PM', 3000, 2686, 1, '981961510v', 6),
('inv00018', 'GH-7685', '2019-07-22', '04:10:54 AM', 1000, 4300, 2, '981961510v', 6),
('inv00019', 'GH-7685', '2019-07-22', '04:21:09 AM', 2000, 5500, 1, '981961510v', 6),
('inv00020', 'GH-7685', '2019-07-22', '04:23:47 AM', 2000, 5500, 1, '981961510v', 6),
('inv00021', 'GH-7685', '2019-07-22', '08:57:22 AM', 2000, 2700, 2, '981961510v', 6),
('inv00022', 'GH-7685', '2019-07-22', '08:59:45 AM', 2000, 5500, 1, '981961510v', 6),
('inv00023', 'GH-7685', '2019-07-22', '09:15:28 AM', 2000, 5500, 1, '981961510v', 6),
('inv00024', 'CAB-7845', '2019-07-22', '11:30:44 AM', 800, 2200, 1, '931234567v', 6),
('inv00025', 'GH-7685', '2019-07-22', '12:29:38 PM', 1000, 2800, 1, '981961510v', 6),
('INV0013', 'GH-7685', '2019-07-19', '07:19:39 PM', 3200, 2000, 1, '981961510v', 6),
('INV0014', 'GH-7685', '2019-07-19', '07:35:08 PM', 3200, 2000, 1, '981961510v', 6),
('INV0015', 'GH-7685', '2019-07-19', '08:44:39 PM', 3800, 2000, 1, '981961510v', 6),
('INV0016', 'GH-7685', '2019-07-20', '11:10:39 AM', 2000, 2000, 1, '981961510v', 6),
('INV0017', 'GH-7685', '2019-07-20', '11:15:12 AM', 3000, 2588, 1, '981961510v', 6);

-- --------------------------------------------------------

--
-- Table structure for table `invoice_items`
--

DROP TABLE IF EXISTS `invoice_items`;
CREATE TABLE IF NOT EXISTS `invoice_items` (
  `idinvoice_items` int(11) NOT NULL AUTO_INCREMENT,
  `unitprice` double DEFAULT NULL,
  `total_items_price` double DEFAULT NULL,
  `qty` double DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `idinvoice` varchar(45) NOT NULL,
  `item_iditem` varchar(45) NOT NULL,
  PRIMARY KEY (`idinvoice_items`),
  KEY `fk_invoice_items_invoice_11` (`idinvoice`),
  KEY `fk_invoice_items_item1` (`item_iditem`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `invoice_items`
--

INSERT INTO `invoice_items` (`idinvoice_items`, `unitprice`, `total_items_price`, `qty`, `total_amount`, `idinvoice`, `item_iditem`) VALUES
(1, 300, 300, 1, 686, 'inv00012', '1'),
(2, 300, 300, 1, 0, 'INV0013', '3'),
(3, 300, 300, 1, 0, 'INV0014', '4'),
(4, 300, 300, 1, 0, 'INV0015', '2'),
(5, 300, 300, 1, 0, 'INV0016', '4'),
(6, 300, 600, 2, 588, 'INV0017', '3'),
(7, 1800, 1800, 1, 4300, 'inv00018', '3'),
(8, 3500, 3500, 1, 5500, 'inv00019', '4'),
(9, 3500, 3500, 1, 5500, 'inv00020', '4'),
(10, 200, 200, 1, 2700, 'inv00021', '1'),
(11, 3500, 3500, 1, 5500, 'inv00022', '4'),
(12, 3500, 3500, 1, 5500, 'inv00023', '4'),
(13, 200, 200, 1, 2200, 'inv00024', '1'),
(14, 300, 300, 1, 2800, 'inv00025', '2');

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
CREATE TABLE IF NOT EXISTS `item` (
  `iditem` varchar(45) NOT NULL,
  `itemname` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`iditem`),
  KEY `fk_item_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`iditem`, `itemname`, `description`, `brand`, `user_userid`) VALUES
('1', 'engine oil', 'NO', 'Lubricant', 1),
('2', 'oil filter', 'NO', 'Lubricant', 1),
('3', 'air filter', 'NO', 'Lubricant', 1),
('4', 'fuel filters', 'NO', 'Lubricant', 1);

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

DROP TABLE IF EXISTS `loan`;
CREATE TABLE IF NOT EXISTS `loan` (
  `loanID` varchar(45) NOT NULL,
  `month` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `nic` varchar(45) NOT NULL,
  `userid` int(11) NOT NULL,
  `idloan_type` int(11) NOT NULL,
  `amount` double DEFAULT NULL,
  `payment` double DEFAULT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`loanID`),
  KEY `fk_loan_employee1` (`nic`),
  KEY `fk_loan_user1_idx` (`userid`),
  KEY `fk_loan_loan_type1_idx` (`idloan_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan`
--

INSERT INTO `loan` (`loanID`, `month`, `status`, `nic`, `userid`, `idloan_type`, `amount`, `payment`, `balance`) VALUES
('L001', 'July', 'active', '123', 6, 3, 10000, 833.3333333333334, 10150),
('L002', 'July', 'active', '941842364v', 6, 3, 10000, 833.3333333333334, 10000),
('L003', 'July', 'active', '199819601510', 6, 5, 8000, 666.6666666666666, 8000);

-- --------------------------------------------------------

--
-- Table structure for table `loan_payment`
--

DROP TABLE IF EXISTS `loan_payment`;
CREATE TABLE IF NOT EXISTS `loan_payment` (
  `idloan_payment` int(11) NOT NULL AUTO_INCREMENT,
  `monthly_payment` double DEFAULT NULL,
  `date` varchar(10) DEFAULT NULL,
  `loanID` varchar(45) NOT NULL,
  PRIMARY KEY (`idloan_payment`),
  KEY `fk_loan_payment_loan1_idx` (`loanID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_payment`
--

INSERT INTO `loan_payment` (`idloan_payment`, `monthly_payment`, `date`, `loanID`) VALUES
(2, 850, '22', 'L001');

-- --------------------------------------------------------

--
-- Table structure for table `loan_type`
--

DROP TABLE IF EXISTS `loan_type`;
CREATE TABLE IF NOT EXISTS `loan_type` (
  `idloan_type` int(11) NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  PRIMARY KEY (`idloan_type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_type`
--

INSERT INTO `loan_type` (`idloan_type`, `amount`) VALUES
(1, 500),
(2, 300),
(3, 10000),
(4, 15000),
(5, 8000);

-- --------------------------------------------------------

--
-- Table structure for table `login_history`
--

DROP TABLE IF EXISTS `login_history`;
CREATE TABLE IF NOT EXISTS `login_history` (
  `idlogin_history` int(11) NOT NULL AUTO_INCREMENT,
  `date` varchar(45) DEFAULT NULL,
  `intime` varchar(45) DEFAULT NULL,
  `outtime` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idlogin_history`),
  KEY `fk_login_history_user1_idx` (`user_userid`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login_history`
--

INSERT INTO `login_history` (`idlogin_history`, `date`, `intime`, `outtime`, `status`, `user_userid`) VALUES
(1, '2019-07-11', '11:08:53', NULL, 'login', 1),
(2, '2019-07-11', '11:09:43', NULL, 'login', 1),
(3, '2019-07-11', '11:11:38', '11:13:24', 'logout', 2),
(4, '2019-07-12', '13:14:01', NULL, 'login', 1),
(5, '2019-07-14', '08:51:07', NULL, 'login', 1),
(6, '2019-07-14', '08:53:18', NULL, 'login', 1),
(7, '2019-07-15', '14:26:38', NULL, 'login', 1),
(8, '2019-07-16', '09:31:20', NULL, 'login', 1),
(9, '2019-07-16', '09:32:09', NULL, 'login', 1),
(10, '2019-07-16', '10:07:04', NULL, 'login', 1),
(11, '2019-07-16', '10:08:30', NULL, 'login', 1),
(12, '2019-07-19', '16:59:54', '17:00:09', 'logout', 6),
(13, '2019-07-19', '17:01:30', '17:02:02', 'logout', 6),
(14, '2019-07-19', '17:01:49', '17:02:02', 'logout', 6),
(15, '2019-07-19', '17:56:30', '18:02:40', 'logout', 6),
(16, '2019-07-19', '18:00:11', '18:02:40', 'logout', 6),
(17, '2019-07-19', '18:02:28', '18:02:40', 'logout', 6),
(18, '2019-07-19', '18:38:50', '18:39:25', 'logout', 6),
(19, '2019-07-19', '19:01:52', '19:07:22', 'logout', 6),
(20, '2019-07-19', '19:19:13', '19:19:59', 'logout', 6),
(21, '2019-07-19', '19:34:44', '19:38:08', 'logout', 6),
(22, '2019-07-19', '19:45:18', '19:46:01', 'logout', 6),
(23, '2019-07-19', '19:46:11', '19:46:16', 'logout', 6),
(24, '2019-07-19', '20:44:07', '21:31:11', 'logout', 6),
(25, '2019-07-20', '11:01:58', '11:11:01', 'logout', 6),
(26, '2019-07-20', '11:14:39', '11:24:19', 'logout', 6),
(27, '2019-07-20', '11:53:40', '13:50:20', 'logout', 6),
(28, '2019-07-20', '13:47:53', '13:50:20', 'logout', 6),
(29, '2019-07-20', '14:31:31', '14:31:52', 'logout', 6),
(30, '2019-07-20', '15:57:35', '15:57:46', 'logout', 6),
(31, '2019-07-20', '16:07:24', '16:07:37', 'logout', 6),
(32, '2019-07-21', '13:59:31', '14:09:13', 'logout', 6),
(33, '2019-07-21', '14:05:25', '14:09:13', 'logout', 6),
(34, '2019-07-21', '14:09:47', '14:11:44', 'logout', 6),
(35, '2019-07-21', '14:10:47', '14:11:44', 'logout', 6),
(36, '2019-07-21', '14:13:44', '14:14:56', 'logout', 6),
(37, '2019-07-21', '14:22:56', '14:27:33', 'logout', 6),
(38, '2019-07-21', '14:40:40', '14:47:05', 'logout', 6),
(39, '2019-07-21', '14:45:08', '14:47:05', 'logout', 6),
(40, '2019-07-21', '14:45:48', '14:47:05', 'logout', 6),
(41, '2019-07-21', '14:48:22', '14:49:04', 'logout', 6),
(42, '2019-07-21', '23:35:37', '23:42:00', 'logout', 6),
(43, '2019-07-21', '23:41:34', '23:42:00', 'logout', 6),
(44, '2019-07-21', '23:44:43', '23:45:39', 'logout', 6),
(45, '2019-07-21', '23:47:30', '23:51:13', 'logout', 6),
(46, '2019-07-22', '00:01:16', '00:02:26', 'logout', 6),
(47, '2019-07-22', '00:03:54', '00:15:40', 'logout', 6),
(48, '2019-07-22', '00:04:44', '00:15:40', 'logout', 6),
(49, '2019-07-22', '00:07:05', '00:15:40', 'logout', 6),
(50, '2019-07-22', '00:08:33', '00:15:40', 'logout', 6),
(51, '2019-07-22', '00:15:08', '00:15:40', 'logout', 6),
(52, '2019-07-22', '00:20:57', '00:21:32', 'logout', 6),
(53, '2019-07-22', '03:47:32', '03:51:42', 'logout', 6),
(54, '2019-07-22', '03:55:16', '04:16:29', 'logout', 6),
(55, '2019-07-22', '04:09:51', '04:16:29', 'logout', 6),
(56, '2019-07-22', '04:20:45', '04:21:34', 'logout', 6),
(57, '2019-07-22', '04:23:20', '04:24:08', 'logout', 6),
(58, '2019-07-22', '05:56:54', '06:06:10', 'logout', 6),
(59, '2019-07-22', '05:59:22', '06:06:10', 'logout', 6),
(60, '2019-07-22', '06:05:14', '06:06:10', 'logout', 6),
(61, '2019-07-22', '06:10:06', '06:14:22', 'logout', 6),
(62, '2019-07-22', '08:45:42', '08:51:43', 'logout', 6),
(63, '2019-07-22', '08:52:16', '08:55:44', 'logout', 6),
(64, '2019-07-22', '08:54:29', '08:55:44', 'logout', 6),
(65, '2019-07-22', '08:56:12', '08:59:58', 'logout', 6),
(66, '2019-07-22', '09:15:09', '09:15:46', 'logout', 6),
(67, '2019-07-22', '09:24:09', '09:25:10', 'logout', 6),
(68, '2019-07-22', '09:40:53', '09:44:12', 'logout', 6),
(69, '2019-07-22', '09:45:13', '09:48:56', 'logout', 6),
(70, '2019-07-22', '09:48:34', '09:48:56', 'logout', 6),
(71, '2019-07-22', '10:10:43', '10:20:34', 'logout', 6),
(72, '2019-07-22', '10:14:10', '10:20:34', 'logout', 6),
(73, '2019-07-22', '10:19:02', '10:20:34', 'logout', 6),
(74, '2019-07-22', '10:20:54', '10:21:44', 'logout', 6),
(75, '2019-07-22', '10:24:25', '10:34:09', 'logout', 6),
(76, '2019-07-22', '10:34:30', '10:35:50', 'logout', 6),
(77, '2019-07-22', '10:36:16', '10:40:48', 'logout', 6),
(78, '2019-07-22', '10:43:22', '10:55:14', 'logout', 6),
(79, '2019-07-22', '11:00:11', '11:13:30', 'logout', 6),
(80, '2019-07-22', '11:04:08', '11:13:30', 'logout', 6),
(81, '2019-07-22', '11:11:45', '11:13:30', 'logout', 6),
(82, '2019-07-22', '11:14:31', '11:16:14', 'logout', 6),
(83, '2019-07-22', '11:17:18', '11:17:39', 'logout', 6),
(84, '2019-07-22', '11:27:47', '11:32:46', 'logout', 6),
(85, '2019-07-22', '12:09:01', '12:13:52', 'logout', 7),
(86, '2019-07-22', '12:14:15', '12:18:13', 'logout', 6),
(87, '2019-07-22', '12:22:20', '13:33:47', 'logout', 6),
(88, '2019-07-31', '10:35:11', NULL, 'login', 6);

-- --------------------------------------------------------

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
CREATE TABLE IF NOT EXISTS `package` (
  `idpackage` varchar(10) NOT NULL,
  `service_type` varchar(45) DEFAULT NULL,
  `item1` varchar(45) DEFAULT NULL,
  `item2` varchar(45) DEFAULT NULL,
  `item3` varchar(45) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  PRIMARY KEY (`idpackage`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `package`
--

INSERT INTO `package` (`idpackage`, `service_type`, `item1`, `item2`, `item3`, `discount`) VALUES
('P1', 'full body', 'oil_filter', 'air_filter', 'fuel_filter', 500),
('P2', 'full body', 'disable', 'air_filter', 'fuel_filter', 500);

-- --------------------------------------------------------

--
-- Table structure for table `payroll`
--

DROP TABLE IF EXISTS `payroll`;
CREATE TABLE IF NOT EXISTS `payroll` (
  `idpayroll` int(11) NOT NULL,
  `month` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `workingdays` int(11) DEFAULT NULL,
  `leave_charges` double DEFAULT NULL,
  `bonus` double DEFAULT NULL,
  `attendence_bonus` double DEFAULT NULL,
  `totalsalary` double DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `loan_idloan` varchar(45) NOT NULL,
  `employee_nic` varchar(45) NOT NULL,
  PRIMARY KEY (`idpayroll`),
  KEY `fk_payroll_with_loan_loan1` (`loan_idloan`),
  KEY `fk_payroll_with_loan_employee1` (`employee_nic`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payroll`
--

INSERT INTO `payroll` (`idpayroll`, `month`, `year`, `workingdays`, `leave_charges`, `bonus`, `attendence_bonus`, `totalsalary`, `date`, `status`, `loan_idloan`, `employee_nic`) VALUES
(1, 'Jul', '2019', 22, 500, 1000, 1000, 15700, '22', 'Active', 'L001', '123');

-- --------------------------------------------------------

--
-- Table structure for table `po`
--

DROP TABLE IF EXISTS `po`;
CREATE TABLE IF NOT EXISTS `po` (
  `poid` varchar(45) NOT NULL,
  `date1` date DEFAULT NULL,
  `date2` date DEFAULT NULL,
  `suppliers_idsuppliers` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`poid`),
  KEY `fk_supplierid_idx` (`suppliers_idsuppliers`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `po_item`
--

DROP TABLE IF EXISTS `po_item`;
CREATE TABLE IF NOT EXISTS `po_item` (
  `po_itemid` varchar(10) NOT NULL,
  `po_poid` varchar(45) DEFAULT NULL,
  `item_iditem` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`po_itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `reminder`
--

DROP TABLE IF EXISTS `reminder`;
CREATE TABLE IF NOT EXISTS `reminder` (
  `idreminder` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  ` mileage` double DEFAULT NULL,
  `idinvoice` varchar(45) NOT NULL,
  `status` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`idreminder`),
  KEY `fk_reminder_invoice1` (`idinvoice`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reminder`
--

INSERT INTO `reminder` (`idreminder`, `date`, ` mileage`, `idinvoice`, `status`) VALUES
(1, '2019-07-22', 3200, 'INV0014', 'Active'),
(2, '2019-07-22', 500, 'INV0013', 'Active'),
(3, '2019-09-17', 3800, 'INV0015', 'Active'),
(4, '2019-09-18', 2000, 'INV0016', 'Active'),
(5, '2019-09-18', 3000, 'INV0017', 'Active'),
(6, '2019-09-20', 2000, 'inv00020', 'Active'),
(7, '2019-09-20', 2000, 'inv00021', 'Active'),
(8, '2019-09-20', 2000, 'inv00022', 'Active'),
(9, '2019-09-20', 2000, 'inv00023', 'Active'),
(10, '2019-07-22', 800, 'inv00024', 'Active'),
(11, '2019-09-20', 1000, 'inv00025', 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `reset_passsword`
--

DROP TABLE IF EXISTS `reset_passsword`;
CREATE TABLE IF NOT EXISTS `reset_passsword` (
  `idreset_passsword` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `code` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idreset_passsword`),
  KEY `fk_reset_passsword_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `service_type`
--

DROP TABLE IF EXISTS `service_type`;
CREATE TABLE IF NOT EXISTS `service_type` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicle_type_v_type` varchar(45) NOT NULL,
  `service_type` varchar(45) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `duration` time DEFAULT NULL,
  PRIMARY KEY (`service_id`),
  KEY `fk_service_type_vehicle_type1` (`vehicle_type_v_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service_type`
--

INSERT INTO `service_type` (`service_id`, `vehicle_type_v_type`, `service_type`, `amount`, `duration`) VALUES
(1, 'car', 'full body', 2000, '05:02:10'),
(2, 'car', 'full  body', 500, '02:10:02'),
(3, 'bus', 'Wash and oil', 5000, '03:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock` (
  `idstock` int(11) NOT NULL AUTO_INCREMENT,
  `qty` double DEFAULT NULL,
  `buyingunitprice` double DEFAULT NULL,
  `item_iditem` varchar(45) NOT NULL,
  `sellingunitprice` double DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idstock`),
  KEY `fk_stock_item1` (`item_iditem`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`idstock`, `qty`, `buyingunitprice`, `item_iditem`, `sellingunitprice`, `status`) VALUES
(1, 4, 100, '1', 200, 'good'),
(2, 13, 1000, '2', 1500, 'good'),
(3, 25, 1000, '3', 1500, 'good'),
(4, 9, 2700, '4', 3500, 'good');

-- --------------------------------------------------------

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
CREATE TABLE IF NOT EXISTS `suppliers` (
  `idsuppliers` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `nic` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `time` varchar(45) DEFAULT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idsuppliers`),
  KEY `fk_suppliers_user1_idx` (`user_userid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `suppliers`
--

INSERT INTO `suppliers` (`idsuppliers`, `name`, `nic`, `mobile`, `address`, `status`, `date`, `time`, `user_userid`) VALUES
('S001', 'Pathum Holdings', '981400364v', '0770806841', 'Katuwallegama Road,Minuwangoda ', 'active', '2019-07-22', '03:49:09', 6),
('S002', 'srani', '555555555v', '0112299402', 'sddd', 'active', '2019-07-22', '12:27:43', 6);

-- --------------------------------------------------------

--
-- Table structure for table `top_customer`
--

DROP TABLE IF EXISTS `top_customer`;
CREATE TABLE IF NOT EXISTS `top_customer` (
  `idtop_customer` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `mobile` int(11) DEFAULT NULL,
  `v_no` varchar(45) DEFAULT NULL,
  `customer_cus_nic` varchar(45) NOT NULL,
  `user_userid` int(11) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idtop_customer`),
  KEY `fk_top_customer_customer1_idx` (`customer_cus_nic`),
  KEY `fk_top_customer_user1_idx` (`user_userid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `top_customer`
--

INSERT INTO `top_customer` (`idtop_customer`, `name`, `mobile`, `v_no`, `customer_cus_nic`, `user_userid`, `status`) VALUES
(1, 'sarani', 11, NULL, '1', 1, 'Active'),
(2, 'dad', 22, NULL, '2', 1, 'Active'),
(3, 'jm', 33, NULL, '3', 1, 'Active');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `time` time DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `usertype` varchar(45) DEFAULT NULL,
  `nic` varchar(45) DEFAULT NULL,
  `image` varchar(450) DEFAULT NULL,
  `mobile` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userid`, `username`, `password`, `date`, `time`, `status`, `email`, `usertype`, `nic`, `image`, `mobile`) VALUES
(1, 'sarani', '123', '2019-07-09', '09:46:19', 'Deactive', 'sarani@gmail.com', 'Admin', '546', 'E:/photo/My/BeautyPlusMe_20180730152122_fast.jpg', 456),
(2, 'nethin', '456', '2019-05-03', '10:50:10', 'Deactive', 'nethin@gmail.com', 'User', '2', 'E:/photo/My/BeautyPlusMe_20180730152122_fast.jpg', 159),
(3, 'madura', '789', '2019-07-10', '11:25:17', 'Deactive', 'madura@gmail.com', 'Admin', '26', 'null', 852),
(4, 'admin', '1234', '2019-07-11', '07:47:18', 'Deactive', 'admin@gmail.com', 'Admin', '123', 'E:/doctor-patient-icon-Female_Doctor.png', 77115588),
(5, 'new', '4567', '2019-07-11', '07:59:04', 'Deactive', 'new@gmail.com', 'User', '852', 'E:/58af23649c85f28a7d596244db85173a.jpg', 789),
(6, 'hasun', '1234', '2019-07-10', '16:56:35', 'Active', 'hasunnilupul@icloud.com', 'Admin', '199819601510', 'C:/Users/Hasun Nilupul/Pictures/icons/Employee_96px.png', 783462499),
(7, 'nilupul', '12345', '2019-07-05', '12:55:20', 'Active', 'hasunnilupul16@gmail.com', 'User', '981961510v', 'C:/Users/Hasun Nilupul/Pictures/icons/Employee_96px.png', 784728616),
(8, 'pathum', 'aaa', '2019-07-22', '12:11:32', 'Active', 'pathum@gmail.com', 'User', '976790820v', 'C:/Users/Hasun Nilupul/Pictures/icons/house-for-sale-Icon.png', 778535228);

-- --------------------------------------------------------

--
-- Table structure for table `vehicle_type`
--

DROP TABLE IF EXISTS `vehicle_type`;
CREATE TABLE IF NOT EXISTS `vehicle_type` (
  `v_type` varchar(45) NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`v_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicle_type`
--

INSERT INTO `vehicle_type` (`v_type`, `status`) VALUES
('bus', 'active'),
('car', 'active'),
('Jeep', 'active'),
('van', 'active');

-- --------------------------------------------------------

--
-- Table structure for table `working_days_for_month`
--

DROP TABLE IF EXISTS `working_days_for_month`;
CREATE TABLE IF NOT EXISTS `working_days_for_month` (
  `idworking_days_for_month` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `working_days_count` int(11) DEFAULT NULL,
  `user_userid` int(11) NOT NULL,
  PRIMARY KEY (`idworking_days_for_month`),
  KEY `fk_working_days_for_month_user1_idx` (`user_userid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `working_days_for_month`
--

INSERT INTO `working_days_for_month` (`idworking_days_for_month`, `month`, `year`, `working_days_count`, `user_userid`) VALUES
(7, 'Jul', '2019', 25, 6);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `attendence`
--
ALTER TABLE `attendence`
  ADD CONSTRAINT `fk_attendence_employee1` FOREIGN KEY (`employee_nic`) REFERENCES `employee` (`nic`),
  ADD CONSTRAINT `fk_attendence_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `fk_booking_service_type1` FOREIGN KEY (`service_id`) REFERENCES `service_type` (`service_id`),
  ADD CONSTRAINT `fk_booking_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `cash_payment`
--
ALTER TABLE `cash_payment`
  ADD CONSTRAINT `fk_cash_payment_grn1` FOREIGN KEY (`grn_idgrn`) REFERENCES `grn` (`idgrn`),
  ADD CONSTRAINT `fk_cash_payment_suppliers1` FOREIGN KEY (`suppliers_idsuppliers`) REFERENCES `suppliers` (`idsuppliers`);

--
-- Constraints for table `check_payment`
--
ALTER TABLE `check_payment`
  ADD CONSTRAINT `fk_check_payment_grn1` FOREIGN KEY (`grn_idgrn`) REFERENCES `grn` (`idgrn`),
  ADD CONSTRAINT `fk_check_payment_suppliers1` FOREIGN KEY (`suppliers_idsuppliers`) REFERENCES `suppliers` (`idsuppliers`);

--
-- Constraints for table `customer`
--
ALTER TABLE `customer`
  ADD CONSTRAINT `fk_customer_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `customers_vehicles`
--
ALTER TABLE `customers_vehicles`
  ADD CONSTRAINT `fk_customers_vehicles_vehicle_type1` FOREIGN KEY (`v_type`) REFERENCES `vehicle_type` (`v_type`),
  ADD CONSTRAINT `fk_vehicles_customer1` FOREIGN KEY (`cus_nic`) REFERENCES `customer` (`cus_nic`);

--
-- Constraints for table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `fk_employee_employee_salary1` FOREIGN KEY (`employee_salary_salaryid`) REFERENCES `employee_salary` (`salaryid`);

--
-- Constraints for table `employee_salary`
--
ALTER TABLE `employee_salary`
  ADD CONSTRAINT `fk_employee_salary_emp_type1` FOREIGN KEY (`emp_type`) REFERENCES `emp_type` (`id`),
  ADD CONSTRAINT `fk_employee_salary_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `emp_leave`
--
ALTER TABLE `emp_leave`
  ADD CONSTRAINT `fk_leave_employee1` FOREIGN KEY (`employee_nic`) REFERENCES `employee` (`nic`),
  ADD CONSTRAINT `fk_leave_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `grn`
--
ALTER TABLE `grn`
  ADD CONSTRAINT `fk_grn_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `grn_item`
--
ALTER TABLE `grn_item`
  ADD CONSTRAINT `fk_grn_item_grn1` FOREIGN KEY (`grn_idgrn`) REFERENCES `grn` (`idgrn`),
  ADD CONSTRAINT `fk_grn_item_item1` FOREIGN KEY (`item_iditem`) REFERENCES `item` (`iditem`);

--
-- Constraints for table `invoice`
--
ALTER TABLE `invoice`
  ADD CONSTRAINT `fk_invoice_customer1` FOREIGN KEY (`customer_cus_nic`) REFERENCES `customer` (`cus_nic`),
  ADD CONSTRAINT `fk_invoice_service_type1` FOREIGN KEY (`service_type_service_id`) REFERENCES `service_type` (`service_id`),
  ADD CONSTRAINT `fk_invoice_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `invoice_items`
--
ALTER TABLE `invoice_items`
  ADD CONSTRAINT `fk_invoice_items_invoice_11` FOREIGN KEY (`idinvoice`) REFERENCES `invoice` (`idinvoice`),
  ADD CONSTRAINT `fk_invoice_items_item1` FOREIGN KEY (`item_iditem`) REFERENCES `item` (`iditem`);

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `fk_item_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `loan`
--
ALTER TABLE `loan`
  ADD CONSTRAINT `fk_loan_employee1` FOREIGN KEY (`nic`) REFERENCES `employee` (`nic`),
  ADD CONSTRAINT `fk_loan_loan_type1` FOREIGN KEY (`idloan_type`) REFERENCES `loan_type` (`idloan_type`),
  ADD CONSTRAINT `fk_loan_user1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `loan_payment`
--
ALTER TABLE `loan_payment`
  ADD CONSTRAINT `fk_loan_payment_loan1` FOREIGN KEY (`loanID`) REFERENCES `loan` (`loanID`);

--
-- Constraints for table `login_history`
--
ALTER TABLE `login_history`
  ADD CONSTRAINT `fk_login_history_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `payroll`
--
ALTER TABLE `payroll`
  ADD CONSTRAINT `fk_payroll_with_loan_employee1` FOREIGN KEY (`employee_nic`) REFERENCES `employee` (`nic`),
  ADD CONSTRAINT `fk_payroll_with_loan_loan1` FOREIGN KEY (`loan_idloan`) REFERENCES `loan` (`loanID`);

--
-- Constraints for table `reminder`
--
ALTER TABLE `reminder`
  ADD CONSTRAINT `fk_reminder_invoice1` FOREIGN KEY (`idinvoice`) REFERENCES `invoice` (`idinvoice`);

--
-- Constraints for table `reset_passsword`
--
ALTER TABLE `reset_passsword`
  ADD CONSTRAINT `fk_reset_passsword_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `service_type`
--
ALTER TABLE `service_type`
  ADD CONSTRAINT `fk_service_type_vehicle_type1` FOREIGN KEY (`vehicle_type_v_type`) REFERENCES `vehicle_type` (`v_type`);

--
-- Constraints for table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `fk_stock_item1` FOREIGN KEY (`item_iditem`) REFERENCES `item` (`iditem`);

--
-- Constraints for table `suppliers`
--
ALTER TABLE `suppliers`
  ADD CONSTRAINT `fk_suppliers_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `top_customer`
--
ALTER TABLE `top_customer`
  ADD CONSTRAINT `fk_top_customer_customer1` FOREIGN KEY (`customer_cus_nic`) REFERENCES `customer` (`cus_nic`),
  ADD CONSTRAINT `fk_top_customer_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);

--
-- Constraints for table `working_days_for_month`
--
ALTER TABLE `working_days_for_month`
  ADD CONSTRAINT `fk_working_days_for_month_user1` FOREIGN KEY (`user_userid`) REFERENCES `user` (`userid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
