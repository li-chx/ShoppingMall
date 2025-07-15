/*
 Navicat Premium Data Transfer

 Source Server         : @localhost
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : shopping_mall

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 14/07/2025 13:58:40
*/
USE shopping_mall;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人',
  `useraddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '地址信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (5, 2, 'mmm', '武汉理工大学', '18888888888');

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '管理员', '/files/2024-11-27_13-54-51_R.jpg', 'ADMIN', '13677889922', 'admin@sm.com');

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺介绍',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '店铺审核状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '店铺信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES (7, '化妆品', '123', 'ShoppingMall化妆品官方旗舰店', '/files/2024-11-28_11-45-24_OIP-C.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall化妆品，100年专注化妆，科学护肤，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (8, '户外运动', '123', 'ShoppingMall户外运动官方旗舰店', '/files/2024-11-28_11-58-02_户外运动.png', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall户外运动，100年专注户外运动，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (9, '家用电器', '123', 'ShoppingMall家用电器官方旗舰店', '/files/2024-11-28_12-08-07_家用电器.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall家用电器，100年专注家用电器，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (10, '母婴用品', '123', 'ShoppingMall母婴用品官方旗舰店', '/files/2024-12-15_15-02-56_母婴用品.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall母婴用品，100年专注母婴用品，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (12, '水果', '123', 'ShoppingMall果蔬官方旗舰店', '/files/2024-12-15_14-47-10_水果.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall果蔬，100年专注果蔬，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (13, '数码', '123', 'ShoppingMall数码官方旗舰店', '/files/2024-12-15_14-58-18_数码.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall数码，100年专注数码，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (14, '图书', '123', 'ShoppingMall图书官方旗舰店', '/files/2024-12-15_15-09-09_图书.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall图书，100年专注图书，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (15, '汽车配件', '123', 'ShoppingMall汽车配件官方旗舰店', '/files/2024-12-15_15-15-25_汽车.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall汽车配件，100年专注汽车配件，你值得拥有', '审核通过');
INSERT INTO `business` VALUES (16, '箱包', '123', 'ShoppingMall箱包官方旗舰店', '/files/2024-12-15_15-12-34_箱包.jpg', 'BUSINESS', '18888888888', '188@sm.com', 'ShoppingMall箱包，100年专注箱包，你值得拥有', '审核通过');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品ID',
  `business_id` int NULL DEFAULT NULL COMMENT '店铺ID',
  `num` int NULL DEFAULT NULL COMMENT '数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES (13, 2, 20, 13, 1);
INSERT INTO `cart` VALUES (16, 2, 16, 12, 1);
INSERT INTO `cart` VALUES (17, 2, 11, 7, 1);
INSERT INTO `cart` VALUES (18, 2, 14, 7, 7);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类描述',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (3, '女装内衣', '这是女装内衣专栏', '/files/2024-11-27_13-55-24_女装内衣.png');
INSERT INTO `category` VALUES (4, '家用电器', '这是家用电器专栏', '/files/2024-11-27_14-04-54_jiayongdianqi.png');
INSERT INTO `category` VALUES (5, '化妆品', '这是化妆品专栏', '/files/2024-11-27_14-10-57_huazhuangpin.png');
INSERT INTO `category` VALUES (6, '户外运功', '这是户外运动专栏', '/files/2024-11-27_14-11-22_huwaiyundong.png');
INSERT INTO `category` VALUES (7, '家纺家饰', '这是家纺家饰专栏', '/files/2024-11-27_14-11-51_jiafangjiashi.png');
INSERT INTO `category` VALUES (8, '母婴用品', '这是母婴用品专栏', '/files/2024-11-27_14-12-20_muyingwanju.png');
INSERT INTO `category` VALUES (10, '汽车配件', '这是汽车配件专栏', '/files/2024-11-27_14-13-06_qichepeijian.png');
INSERT INTO `category` VALUES (12, '数码手机', '这是数码手机专栏', '/files/2024-11-27_14-14-15_shumashouji.png');
INSERT INTO `category` VALUES (13, '图书音像', '这是图书影像专栏', '/files/2024-11-27_14-14-41_tushuyinxiang.png');
INSERT INTO `category` VALUES (15, '鞋\\箱包', '这是鞋\\箱包专栏', '/files/2024-11-27_14-16-04_xiexiangbao.png');
INSERT INTO `category` VALUES (16, '医药保健', '这是医药保健专栏', '/files/2024-11-27_14-16-41_yiyaobaojian.png');
INSERT INTO `category` VALUES (17, '珠宝饰品', '这是珠宝饰品专栏', '/files/2024-11-27_14-17-06_zhubaoshipin.png');
INSERT INTO `category` VALUES (18, '水果', '这是水果专栏', '/files/2024-11-27_14-37-57_shuiguo.png');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品主图',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '商品介绍',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '计件单位',
  `count` int NULL DEFAULT 0 COMMENT '商品销量',
  `inventory` int NULL DEFAULT 100 COMMENT '商品库存',
  `category_id` int NULL DEFAULT NULL COMMENT '分类ID',
  `business_id` int NULL DEFAULT NULL COMMENT '店铺ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (8, '冰箱', '/files/2024-11-30_18-08-26_冰箱.png', '<p>这个冰箱是最好的</p>', 5000.00, '件', 102, 2, 4, 9);
INSERT INTO `goods` VALUES (11, '面霜', '/files/2024-12-09_15-27-29_huazhuangpin.jpg', '<p>最好的</p>', 100.00, '盒', 6, 94, 5, 7);
INSERT INTO `goods` VALUES (13, '护肤品套装', '/files/2024-12-15_14-43-57_护肤品套装.jpg', '', 500.00, '套', 0, 100, 5, 7);
INSERT INTO `goods` VALUES (14, '精华水', '/files/2024-12-15_14-44-48_jinhuashui.jpg', '', 99.00, '瓶', 2, 98, 5, 7);
INSERT INTO `goods` VALUES (15, '西瓜', '/files/2024-12-15_14-48-19_西瓜.jpg', '', 30.00, '个', 1, 99, 18, 12);
INSERT INTO `goods` VALUES (16, '橙子', '/files/2024-12-15_14-49-12_橙子.jpg', '', 20.00, '箱', 0, 100, 18, 12);
INSERT INTO `goods` VALUES (17, '草莓', '/files/2024-12-15_14-49-45_草莓.jpg', '', 20.00, '斤', 0, 100, 18, 12);
INSERT INTO `goods` VALUES (18, '电脑', '/files/2024-12-15_14-58-30_电脑.png', '<p><img src=\"http://localhost:9090/files/2024-12-15_20-52-49_详情图片_34.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"http://localhost:9090/files/2024-12-15_20-52-58_详情图片_01.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', 9999.00, '件', 0, 100, 12, 13);
INSERT INTO `goods` VALUES (19, '手机', '/files/2024-12-15_14-59-03_手机.png', '', 6999.00, '件', 1, 99, 12, 13);
INSERT INTO `goods` VALUES (20, '耳机', '/files/2024-12-15_14-59-26_耳机.jpg', '', 1999.00, '件', 0, 100, 12, 13);
INSERT INTO `goods` VALUES (21, '奶粉', '/files/2024-12-15_15-03-10_奶粉.jpg', '', 199.00, '瓶', 0, 100, 8, 10);
INSERT INTO `goods` VALUES (22, '奶瓶', '/files/2024-12-15_15-03-43_奶瓶.jpg', '', 59.00, '件', 0, 100, 8, 10);
INSERT INTO `goods` VALUES (23, '四大名著', '/files/2024-12-15_15-09-20_名著.jpg', '', 199.00, '套', 0, 100, 13, 14);
INSERT INTO `goods` VALUES (24, '科幻小说', '/files/2024-12-15_15-09-51_科幻.jpg', '', 59.00, '本', 0, 100, 13, 14);
INSERT INTO `goods` VALUES (25, '行李箱', '/files/2024-12-15_15-12-45_行李箱.jpg', '', 2999.00, '件', 1, 99, 15, 16);
INSERT INTO `goods` VALUES (26, '包', '/files/2024-12-15_15-13-11_包.jpg', '', 19999.00, '件', 0, 100, 15, 16);
INSERT INTO `goods` VALUES (27, '汽车轮胎', '/files/2024-12-15_15-15-38_轮胎.jpg', '', 1999.00, '个', 0, 100, 10, 15);
INSERT INTO `goods` VALUES (28, '汽车零部件', '/files/2024-12-15_15-16-16_零部件.jpg', '', 199.00, '个', 0, 100, 10, 15);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (4, 'ShoppingMall开业大吉', 'ShoppingMall开业大吉，今天全场5折！！！', '2024-12-01', 'admin');
INSERT INTO `notice` VALUES (5, '疯狂周末', '凡周末化妆品、鞋\\包、户外运动等专栏商品8折起！！！', '2024-12-01', 'admin');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单ID',
  `goods_id` int NULL DEFAULT NULL COMMENT '商品ID',
  `business_id` int NULL DEFAULT NULL COMMENT '商家ID',
  `num` int NULL DEFAULT NULL COMMENT '商品数量',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '订单价格',
  `address_id` int NULL DEFAULT NULL COMMENT '地址ID',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (4, '20241209160453', 8, 9, 3, 2, 15000.00, 5, '待发货');
INSERT INTO `orders` VALUES (9, '20241215203402', 15, 12, 1, 2, 30.00, 5, '待发货');
INSERT INTO `orders` VALUES (10, '20241215203402', 14, 7, 2, 2, 198.00, 5, '待发货');
INSERT INTO `orders` VALUES (11, '20241215203402', 19, 13, 1, 2, 6999.00, 5, '待发货');
INSERT INTO `orders` VALUES (12, '20241215203402', 25, 16, 1, 2, 2999.00, 5, '待发货');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'mmm', '123', 'mmm', '/files/2024-12-01_19-03-50_头像.jpeg', 'USER', '18888888888', '188@sm.com');
INSERT INTO `user` VALUES (5, '1', '123', '1', NULL, 'USER', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
