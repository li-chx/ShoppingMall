/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19  Distrib 10.11.13-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: shopping_mall
-- ------------------------------------------------------
-- Server version	9.3.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `shopping_mall`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `shopping_mall` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `shopping_mall`;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收货人',
  `useraddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '收货地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='地址信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES
(5,2,'mmm','武汉理工大学','18888888888');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES
(1,'admin','admin','管理员','c44f7797-147e-43f7-a2ef-bcd54caa1bf1','ADMIN','13677889922','admin@sm.com');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `business`
--

DROP TABLE IF EXISTS `business`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `business` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '店铺名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '店铺介绍',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '店铺审核状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='店铺信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `business`
--

LOCK TABLES `business` WRITE;
/*!40000 ALTER TABLE `business` DISABLE KEYS */;
INSERT INTO `business` VALUES
(7,'化妆品','123','ShoppingMall化妆品官方旗舰店','a57351ad-1d0c-4935-b848-340eade3bc5b','BUSINESS','18888888888','188@sm.com','ShoppingMall化妆品，100年专注化妆，科学护肤，你值得拥有','审核通过'),
(8,'户外运动','123','ShoppingMall户外运动官方旗舰店','58db155f-1dca-44d1-9e71-959b36954355','BUSINESS','18888888888','188@sm.com','ShoppingMall户外运动，100年专注户外运动，你值得拥有','审核通过'),
(9,'家用电器','123','ShoppingMall家用电器官方旗舰店','12477161-c3f3-4460-a9ac-642e7f8a4ec1','BUSINESS','18888888888','188@sm.com','ShoppingMall家用电器，100年专注家用电器，你值得拥有','审核通过'),
(10,'母婴用品','123','ShoppingMall母婴用品官方旗舰店','75973325-481d-4358-85ef-dd56a7a083fd','BUSINESS','18888888888','188@sm.com','ShoppingMall母婴用品，100年专注母婴用品，你值得拥有','审核通过'),
(12,'水果','123','ShoppingMall果蔬官方旗舰店','60e11569-f1b0-4d21-b61d-2ebd9ef9ee85','BUSINESS','18888888888','188@sm.com','ShoppingMall果蔬，100年专注果蔬，你值得拥有','审核通过'),
(13,'数码','123','ShoppingMall数码官方旗舰店','66959ead-4049-4e37-b59a-003457611a56','BUSINESS','18888888888','188@sm.com','ShoppingMall数码，100年专注数码，你值得拥有','审核通过'),
(14,'图书','123','ShoppingMall图书官方旗舰店','c32b6f34-c9d3-4d9c-b277-1e682cea88bd','BUSINESS','18888888888','188@sm.com','ShoppingMall图书，100年专注图书，你值得拥有','审核通过'),
(15,'汽车配件','123','ShoppingMall汽车配件官方旗舰店','eda0d65b-a2ae-4d48-b46c-359bdc481a22','BUSINESS','18888888888','188@sm.com','ShoppingMall汽车配件，100年专注汽车配件，你值得拥有','审核通过'),
(16,'箱包','123','ShoppingMall箱包官方旗舰店','6a1a56d3-188a-4d5b-902f-01f9e4157efc','BUSINESS','18888888888','188@sm.com','ShoppingMall箱包，100年专注箱包，你值得拥有','审核通过');
/*!40000 ALTER TABLE `business` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `goods_id` int DEFAULT NULL COMMENT '商品ID',
  `business_id` int DEFAULT NULL COMMENT '店铺ID',
  `num` int DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='购物车表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES
(13,2,20,13,1),
(16,2,16,12,1),
(17,2,11,7,1),
(18,2,14,7,7),
(19,2,15,12,1);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '分类描述',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='商品分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES
(3,'女装内衣','这是女装内衣专栏','2a8c4f58-c00d-49d3-afe1-3e9c2f4b0e21'),
(4,'家用电器','这是家用电器专栏','e4a889f7-270d-4279-ad72-1308de5d810c'),
(5,'化妆品','这是化妆品专栏','a7217d29-45bd-48c3-8109-7aacb4dff009'),
(6,'户外运功','这是户外运动专栏','b5609bb2-08af-4470-bcd8-ff5c8d0d5d6b'),
(7,'家纺家饰','这是家纺家饰专栏','7e293f8e-1a86-4e30-a20b-0bf426d98e48'),
(8,'母婴用品','这是母婴用品专栏','41f277e2-0aea-4759-97a8-6b6a3bf77079'),
(10,'汽车配件','这是汽车配件专栏','58aa7478-787a-4e91-ad06-441a17599c02'),
(12,'数码手机','这是数码手机专栏','ba207fb6-a7d4-4f02-9637-d6a8fac2376c'),
(13,'图书音像','这是图书影像专栏','c71dde84-72e6-46cd-8125-f493db6285c7'),
(15,'鞋\\箱包','这是鞋\\箱包专栏','ba1461c5-ddad-4654-9db3-5ad27d755bef'),
(16,'医药保健','这是医药保健专栏','19c5cd1b-af3f-4be3-adc2-64b9894a40d5'),
(17,'珠宝饰品','这是珠宝饰品专栏','e9737173-b94f-4bc2-9cbd-482e7b46b899'),
(18,'水果','这是水果专栏','2d22c678-dcd3-42d6-b67b-8f7d1721637d');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品主图',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '商品介绍',
  `price` double(10,2) DEFAULT NULL COMMENT '商品价格',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '计件单位',
  `count` int DEFAULT '0' COMMENT '商品销量',
  `inventory` int DEFAULT '100' COMMENT '商品库存',
  `category_id` int DEFAULT NULL COMMENT '分类ID',
  `business_id` int DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='商品信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES
(8,'冰箱','adfb20ae-7b12-4d65-bac3-65d3884d61b7','<p>这个冰箱是最好的</p>',5000.00,'件',102,2,4,9),
(11,'面霜','64f03bb0-2776-43e6-8c7b-6e42a6130af6','<p>最好的</p>',100.00,'盒',6,94,5,7),
(13,'护肤品套装','8819170c-174c-4cff-9842-707143ac12b2','',500.00,'套',0,100,5,7),
(14,'精华水','5cc0c69e-0cfc-4fbf-bb76-f3780d52822a','',99.00,'瓶',2,98,5,7),
(15,'西瓜','3846e7df-4b4e-4611-9f6a-9a1f08359b5d','',30.00,'个',1,99,18,12),
(16,'橙子','c70ea0e9-e54c-4442-9188-054bdf300abb','',20.00,'箱',0,100,18,12),
(17,'草莓','e60a0b0d-b95b-4f6f-b168-d0d43f5420b3','',20.00,'斤',0,100,18,12),
(18,'电脑','971de849-187e-4db2-b07c-06220313e056','<p><img src=\"http://localhost:9090/files/2024-12-15_20-52-49_详情图片_34.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9090/files/2024-12-15_20-52-58_详情图片_01.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>',9999.00,'件',0,100,12,13),
(19,'手机','5b469c52-9020-436d-82d6-e8c3e5425600','',6999.00,'件',1,99,12,13),
(20,'耳机','8113d9a7-63f6-406e-856c-63e6761bce22','',1999.00,'件',0,100,12,13),
(21,'奶粉','1caee39a-de00-48c4-af87-ef4e10e346e0','',199.00,'瓶',0,100,8,10),
(22,'奶瓶','ea656605-5619-44ee-8963-dc824fbf99c9','',59.00,'件',0,100,8,10),
(23,'四大名著','8b774730-0925-433a-8405-29452c5ef411','',199.00,'套',0,100,13,14),
(24,'科幻小说','90204675-817b-48c7-8800-9d7f4daf0802','',59.00,'本',0,100,13,14),
(25,'行李箱','51661c95-d8c3-45d7-8f41-222b7edd3f76','',2999.00,'件',1,99,15,16),
(26,'包','ceb6616a-2a04-497f-9ea5-86acbba7f9cb','',19999.00,'件',0,100,15,16),
(27,'汽车轮胎','b1053aeb-b2c9-4024-9bc5-86168cc39f4d','',1999.00,'个',0,100,10,15),
(28,'汽车零部件','0fb0c73b-d338-44b6-b0f9-1e2e212aa1d9','',199.00,'个',0,100,10,15);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='公告信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES
(4,'ShoppingMall开业大吉','ShoppingMall开业大吉，今天全场5折！！！','2024-12-01','admin'),
(5,'疯狂周末','凡周末化妆品、鞋\\包、户外运动等专栏商品8折起！！！','2024-12-01','admin');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单ID',
  `goods_id` int DEFAULT NULL COMMENT '商品ID',
  `business_id` int DEFAULT NULL COMMENT '商家ID',
  `num` int DEFAULT NULL COMMENT '商品数量',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `price` double(10,2) DEFAULT NULL COMMENT '订单价格',
  `address_id` int DEFAULT NULL COMMENT '地址ID',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='订单信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES
(4,'20241209160453',8,9,3,2,15000.00,5,'待发货'),
(9,'20241215203402',15,12,1,2,30.00,5,'待发货'),
(10,'20241215203402',14,7,2,2,198.00,5,'待发货'),
(11,'20241215203402',19,13,1,2,6999.00,5,'待发货'),
(12,'20241215203402',25,16,1,2,2999.00,5,'待发货');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `undo_log`
--

DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `undo_log`
--

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES
(2,'mmm','123','mmm','406a07e1-ef53-40d6-9ac2-056a7c3eff1d','USER','18888888888','188@sm.com'),
(5,'1','123','1',NULL,'USER',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-17 16:45:35
