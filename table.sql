-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: tccboot
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8_bin NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'iPhone 6s',4999.00,495),(2,'iMac',19999.00,598),(3,'iWatch',2999.00,3);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_log`
--

DROP TABLE IF EXISTS `item_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(200) COLLATE utf8_bin NOT NULL,
  `item_id` int NOT NULL,
  `amount` int NOT NULL,
  `status` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderId_UNIQUE` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_log`
--

LOCK TABLES `item_log` WRITE;
/*!40000 ALTER TABLE `item_log` DISABLE KEYS */;
INSERT INTO `item_log` VALUES (1,'2020052921395297890',1,1,'confirmed'),(2,'2020052921563792731',2,2,'confirmed'),(3,'2020052922052476908',1,4,'confirmed');
/*!40000 ALTER TABLE `item_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_info`
--

DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_info` (
  `id` varchar(200) COLLATE utf8_bin NOT NULL,
  `user_id` int NOT NULL,
  `item_id` int NOT NULL,
  `item_amount` int NOT NULL,
  `total_price` decimal(10,2) NOT NULL,
  `status` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_info`
--

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES ('2020052921291924851',1,1,1,4999.00,'cancel'),('2020052921395297890',1,1,1,4999.00,'confirmed'),('2020052921440665791',1,1,1,4999.00,'cancel'),('2020052921492484898',1,1,1,4999.00,'cancel'),('2020052921545997789',1,1,1,4999.00,'cancel'),('2020052921563792731',2,2,2,39998.00,'confirmed'),('2020052921590688251',3,3,4,11996.00,'cancel'),('2020052922050483884',3,3,4,11996.00,'cancel'),('2020052922051539054',1,3,4,11996.00,'cancel'),('2020052922052476908',1,1,4,19996.00,'confirmed');
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `red_packet_log`
--

DROP TABLE IF EXISTS `red_packet_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `red_packet_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(200) COLLATE utf8_bin NOT NULL,
  `user_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='红包流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `red_packet_log`
--

LOCK TABLES `red_packet_log` WRITE;
/*!40000 ALTER TABLE `red_packet_log` DISABLE KEYS */;
INSERT INTO `red_packet_log` VALUES (1,'2020052921395297890',1,100.00,'confirmed'),(2,'2020052921440665791',1,100.00,'cancel'),(3,'2020052921492484898',1,100.00,'cancel'),(4,'2020052921545997789',1,100.00,'cancel'),(5,'2020052921563792731',2,1000.00,'confirmed'),(6,'2020052921590688251',3,2000.00,'cancel'),(7,'2020052922050483884',3,2000.00,'cancel'),(8,'2020052922051539054',1,2000.00,'cancel'),(9,'2020052922052476908',1,2000.00,'confirmed');
/*!40000 ALTER TABLE `red_packet_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tcc_transaction_test_a`
--

DROP TABLE IF EXISTS `tcc_transaction_test_a`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tcc_transaction_test_a` (
  `TRANSACTION_ID` int NOT NULL AUTO_INCREMENT,
  `DOMAIN` varchar(100) DEFAULT NULL,
  `GLOBAL_TX_ID` varbinary(32) NOT NULL,
  `BRANCH_QUALIFIER` varbinary(32) NOT NULL,
  `CONTENT` varbinary(8000) DEFAULT NULL,
  `STATUS` int DEFAULT NULL,
  `TRANSACTION_TYPE` int DEFAULT NULL,
  `RETRIED_COUNT` int DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `LAST_UPDATE_TIME` datetime DEFAULT NULL,
  `VERSION` int DEFAULT NULL,
  `IS_DELETE` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`TRANSACTION_ID`),
  UNIQUE KEY `UX_TX_BQ` (`GLOBAL_TX_ID`,`BRANCH_QUALIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tcc_transaction_test_a`
--

LOCK TABLES `tcc_transaction_test_a` WRITE;
/*!40000 ALTER TABLE `tcc_transaction_test_a` DISABLE KEYS */;
/*!40000 ALTER TABLE `tcc_transaction_test_a` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tcc_transaction_test_consumer`
--

DROP TABLE IF EXISTS `tcc_transaction_test_consumer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tcc_transaction_test_consumer` (
  `TRANSACTION_ID` int NOT NULL AUTO_INCREMENT,
  `DOMAIN` varchar(100) DEFAULT NULL,
  `GLOBAL_TX_ID` varbinary(32) NOT NULL,
  `BRANCH_QUALIFIER` varbinary(32) NOT NULL,
  `CONTENT` varbinary(8000) DEFAULT NULL,
  `STATUS` int DEFAULT NULL,
  `TRANSACTION_TYPE` int DEFAULT NULL,
  `RETRIED_COUNT` int DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  `LAST_UPDATE_TIME` datetime DEFAULT NULL,
  `VERSION` int DEFAULT NULL,
  `IS_DELETE` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`TRANSACTION_ID`),
  UNIQUE KEY `UX_TX_BQ` (`GLOBAL_TX_ID`,`BRANCH_QUALIFIER`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tcc_transaction_test_consumer`
--

LOCK TABLES `tcc_transaction_test_consumer` WRITE;
/*!40000 ALTER TABLE `tcc_transaction_test_consumer` DISABLE KEYS */;
/*!40000 ALTER TABLE `tcc_transaction_test_consumer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) COLLATE utf8_bin NOT NULL,
  `wallet_amount` decimal(10,2) NOT NULL,
  `red_packet_amount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Tom',82004.00,7900.00),(2,'Sam',61002.00,9000.00),(3,'Jack',100000.00,10000.00);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallet_log`
--

DROP TABLE IF EXISTS `wallet_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallet_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` varchar(200) COLLATE utf8_bin NOT NULL,
  `user_id` int NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `status` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderId_UNIQUE` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='钱包流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallet_log`
--

LOCK TABLES `wallet_log` WRITE;
/*!40000 ALTER TABLE `wallet_log` DISABLE KEYS */;
INSERT INTO `wallet_log` VALUES (1,'2020052921395297890',1,4899.00,'confirmed'),(2,'2020052921563792731',2,38998.00,'confirmed'),(3,'2020052921590688251',3,9996.00,'cancel'),(4,'2020052922050483884',3,9996.00,'cancel'),(5,'2020052922051539054',1,9996.00,'cancel'),(6,'2020052922052476908',1,17996.00,'confirmed');
/*!40000 ALTER TABLE `wallet_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-29 22:12:17
