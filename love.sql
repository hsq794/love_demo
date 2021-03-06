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
INSERT INTO `goods` VALUES (1, '??????', 4, 42);
INSERT INTO `goods` VALUES (2, '??????', 1, 61);
INSERT INTO `goods` VALUES (3, '????????????', 1, 70);
INSERT INTO `goods` VALUES (4, '??????', 2, 70);
INSERT INTO `goods` VALUES (10, '??????', 1, 50);
INSERT INTO `goods` VALUES (18, '??????', 1, 46);
INSERT INTO `goods` VALUES (19, '??????', 2, 7);
INSERT INTO `goods` VALUES (20, '?????????', 1, 13);
INSERT INTO `goods` VALUES (21, '??????', 2, 30);
INSERT INTO `goods` VALUES (22, '??????', 2, 20);
INSERT INTO `goods` VALUES (23, '??????', 2, 44);
INSERT INTO `goods` VALUES (24, '??????', 3, 20);

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
INSERT INTO `lovevalue` VALUES (1, 40, '??????', 12);
INSERT INTO `lovevalue` VALUES (2, 40, '??????', 16);
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
  `usex` enum('???','???') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ulove` int(11) NOT NULL DEFAULT 0,
  `uphoto` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uaddress` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (27, '?????????', '123456', '???', 48, '11111111111', '???????????????????????????');
INSERT INTO `user` VALUES (28, '??????', '123456', '???', 10, '22222222222', '??????????????????19???302');
INSERT INTO `user` VALUES (29, '????????????', '123456', '???', 40, '44444444444', '????????????');
INSERT INTO `user` VALUES (30, '???C', '123456', '???', 0, '67890654321', '????????????');
INSERT INTO `user` VALUES (31, '??????', '123456', '???', 16, '13879563215', '?????????');
INSERT INTO `user` VALUES (32, '??????', '123456', '???', 20, '15678342675', '??????');
INSERT INTO `user` VALUES (33, '??????', '123456', '???', 20, '17834216909', '???????????????');
INSERT INTO `user` VALUES (34, '???B', '123456', '???', 0, '67890123456', '??????');
INSERT INTO `user` VALUES (35, '???A??????', '123456', '???', 0, '1363789091', '??????');
INSERT INTO `user` VALUES (36, '??????', '123456', '???', 0, '18379444902', '???????????????');
INSERT INTO `user` VALUES (37, '?????????', '123456', '???', 0, '13657245314', '??????');
INSERT INTO `user` VALUES (38, '?????????', '123456', '???', 0, '13456783456', '??????');

SET FOREIGN_KEY_CHECKS = 1;
