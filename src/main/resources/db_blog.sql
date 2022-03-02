/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80028
Source Host           : localhost:3306
Source Database       : db_blog

Target Server Type    : MYSQL
Target Server Version : 80028
File Encoding         : 65001

Date: 2022-02-24 23:29:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog` (
  `bid` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `first_picture` varchar(255) DEFAULT NULL,
  `cid` int NOT NULL,
  `views` mediumtext,
  `is_appreciate` tinyint(1) DEFAULT '1',
  `is_comment` tinyint(1) DEFAULT '1',
  `is_publish` tinyint(1) DEFAULT NULL,
  `write_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `uid` int NOT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_category`;
CREATE TABLE `tb_category` (
  `cid` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `uid` int NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `picture` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `com_id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) NOT NULL,
  `mail` varchar(20) NOT NULL,
  `content` varchar(300) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `reply_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`com_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mail` varchar(20) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `type` int NOT NULL,
  `register_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
