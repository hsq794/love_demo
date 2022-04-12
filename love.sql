/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : love

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 01/04/2022 18:46:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for controller
-- ----------------------------
DROP TABLE IF EXISTS `controller`;
CREATE TABLE `controller`  (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cpassword` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of controller
-- ----------------------------
INSERT INTO `controller` VALUES (1, 'hsq', '123456');
INSERT INTO `controller` VALUES (8, 'hy', '123456');

-- ----------------------------
-- Table structure for donation
-- ----------------------------
DROP TABLE IF EXISTS `donation`;
CREATE TABLE `donation`  (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dtime` date NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  `gid` int(11) NULL DEFAULT NULL,
  `dunmber` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`did`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of donation
-- ----------------------------
INSERT INTO `donation` VALUES (54, '2022-04-01', 27, 1, 12);
INSERT INTO `donation` VALUES (55, '2022-04-01', 28, 2, 10);
INSERT INTO `donation` VALUES (56, '2022-04-01', 29, 2, 16);
INSERT INTO `donation` VALUES (57, '2022-04-01', 32, 4, 10);
INSERT INTO `donation` VALUES (58, '2022-04-01', 31, 18, 8);
INSERT INTO `donation` VALUES (59, '2022-04-01', 33, 3, 10);
INSERT INTO `donation` VALUES (60, '2022-04-01', 33, 3, 10);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `gid` int(11) NOT NULL AUTO_INCREMENT,
  `gname` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `glove` int(11) NOT NULL,
  `gnumber` int(10) NOT NULL,
  PRIMARY KEY (`gid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '书籍', 4, 42);
INSERT INTO `goods` VALUES (2, '衣服', 1, 61);
INSERT INTO `goods` VALUES (3, '生活用品', 1, 70);
INSERT INTO `goods` VALUES (4, '书包', 2, 70);
INSERT INTO `goods` VALUES (10, '铅笔', 1, 50);
INSERT INTO `goods` VALUES (18, '鞋子', 1, 46);
INSERT INTO `goods` VALUES (19, '脸盆', 2, 7);
INSERT INTO `goods` VALUES (20, '文具盒', 1, 13);
INSERT INTO `goods` VALUES (21, '梳子', 2, 30);
INSERT INTO `goods` VALUES (22, '拖鞋', 2, 20);
INSERT INTO `goods` VALUES (23, '水杯', 2, 44);
INSERT INTO `goods` VALUES (24, '零食', 3, 20);

-- ----------------------------
-- Table structure for lovevalue
-- ----------------------------
DROP TABLE IF EXISTS `lovevalue`;
CREATE TABLE `lovevalue`  (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `love` int(11) NOT NULL,
  `lname` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`lid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lovevalue
-- ----------------------------
INSERT INTO `lovevalue` VALUES (1, 40, '小花', 12);
INSERT INTO `lovevalue` VALUES (2, 40, '小丽', 16);
INSERT INTO `lovevalue` VALUES (7, 0, 'hsq', 22);

-- ----------------------------
-- Table structure for receive
-- ----------------------------
DROP TABLE IF EXISTS `receive`;
CREATE TABLE `receive`  (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `runmber` int(10) NOT NULL,
  `rtime` date NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  `gid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of receive
-- ----------------------------
INSERT INTO `receive` VALUES (8, 10, '2022-03-28', 30, 1);
INSERT INTO `receive` VALUES (9, 1, '2022-03-28', 34, 2);
INSERT INTO `receive` VALUES (11, 12, '2022-03-29', 36, 1);
INSERT INTO `receive` VALUES (12, 10, '2022-04-01', 36, 3);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `upassword` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `usex` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ulove` int(11) NOT NULL DEFAULT 0,
  `uphoto` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uaddress` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (27, '胡萝卜', '123456', '女', 48, '11111111111', '江西省抚州市临川区');
INSERT INTO `user` VALUES (28, '苹果', '123456', '女', 10, '22222222222', '农业大学南区19栋302');
INSERT INTO `user` VALUES (29, '小艾同学', '123456', '男', 40, '44444444444', '江西南昌');
INSERT INTO `user` VALUES (30, '小C', '123456', '男', 0, '67890654321', '江西上饶');
INSERT INTO `user` VALUES (31, '黄勇', '123456', '男', 16, '13879563215', '南昌县');
INSERT INTO `user` VALUES (32, '橙子', '123456', '女', 20, '15678342675', '赣州');
INSERT INTO `user` VALUES (33, '可乐', '123456', '男', 20, '17834216909', '八一纪念馆');
INSERT INTO `user` VALUES (34, '小B', '123456', '女', 0, '67890123456', '九江');
INSERT INTO `user` VALUES (35, '小A同学', '123456', '男', 0, '1363789091', '吉安');
INSERT INTO `user` VALUES (36, '雪碧', '123456', '男', 0, '18379444902', '江西省抚州');
INSERT INTO `user` VALUES (37, '小红花', '123456', '女', 0, '13657245314', '地球');
INSERT INTO `user` VALUES (38, '小音箱', '123456', '男', 0, '13456783456', '火星');

SET FOREIGN_KEY_CHECKS = 1;
