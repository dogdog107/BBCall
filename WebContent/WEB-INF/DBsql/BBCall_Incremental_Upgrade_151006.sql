/*
 Navicat Premium Data Transfer

 Source Server         : LocalDB
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : localhost
 Source Database       : bbcall

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : utf-8

 Date: 10/07/2015 02:07:20 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `PUSHMESSAGE`
-- ----------------------------
DROP TABLE IF EXISTS `PUSHMESSAGE`;
CREATE TABLE `PUSHMESSAGE` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_type` varchar(100) DEFAULT NULL,
  `msg_content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`msg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `PUSHMESSAGE`
-- ----------------------------
BEGIN;
INSERT INTO `PUSHMESSAGE` VALUES ('1', 'master_order_accepted', 'BBCall notification - Your order request has been accepted.'), ('2', 'master_order_completed', 'BBCall notification - You have new completed Order.'), ('3', 'user_preorder_accepted', 'BBCall notification - New Master wants to apply your Order.');
COMMIT;

-- ----------------------------
--  Table structure for `REFERDOC`
-- ----------------------------
DROP TABLE IF EXISTS `REFERDOC`;
CREATE TABLE `REFERDOC` (
  `referdoc_id` int(11) NOT NULL AUTO_INCREMENT,
  `referdoc_type` varchar(30) DEFAULT NULL,
  `referdoc_parentno` int(11) DEFAULT NULL,
  `referdoc_level` int(11) DEFAULT NULL,
  `referdoc_price` decimal(10,2) DEFAULT NULL,
  `referdoc_flag` bit(1) DEFAULT NULL,
  `referdoc_pic_url` varchar(255) DEFAULT NULL,
  `referdoc_downpic_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`referdoc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22036 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `REFERDOC`
-- ----------------------------
BEGIN;
INSERT INTO `REFERDOC` VALUES ('11000', '鋁窗工程', '0', '1', '0.00', b'1', null, null), ('11001', '鋁窗檢查', '11000', '2', '20.00', b'0', null, null), ('11002', '鋁窗維修保養', '11000', '2', '40.00', b'1', null, null), ('11003', '安裝鋁窗', '11000', '2', '20.00', b'0', null, null), ('11004', '其他', '11000', '2', '10.00', b'0', null, null), ('12000', '到會服務', '0', '1', '0.00', b'0', null, null), ('12001', '餐飲到會', '12000', '2', '60.00', b'0', null, null), ('12002', '到會派對活動', '12000', '2', '20.00', b'0', null, null), ('12003', '到會貼紙相機', '12000', '2', '20.00', b'0', null, null), ('12004', '其他', '12000', '2', '20.00', b'0', null, null), ('13000', '家居維修', '0', '1', '0.00', b'0', null, null), ('13001', '開鎖', '13000', '2', '50.00', b'0', null, null), ('13002', '電腦維修', '13000', '2', '30.00', b'0', null, null), ('13003', '電器維修', '13000', '2', '30.00', b'0', null, null), ('13004', '家居維修', '13000', '2', '30.00', b'0', null, null), ('13005', '水電維修', '13000', '2', '30.00', b'0', null, null), ('13006', '其他', '13000', '2', '30.00', b'0', null, null), ('14000', '家務助理', '0', '1', '0.00', b'0', null, null), ('14001', '陪月', '14000', '2', '40.00', b'0', null, null), ('14002', '家務助理', '14000', '2', '40.00', b'0', null, null), ('14003', '長者護理', '14000', '2', '40.00', b'0', null, null), ('14004', '保姆', '14000', '2', '40.00', b'0', null, null), ('14005', '其他', '14000', '2', '40.00', b'0', null, null), ('15000', '上門洗衣', '0', '1', '0.00', b'1', null, null), ('15001', '洗衣（小）', '15000', '2', '128.00', b'1', null, null), ('15002', '洗衣（大）', '15000', '2', '288.00', b'1', null, null), ('16000', '寵物服務', '0', '1', '0.00', b'0', null, null), ('16001', '寵物美容', '16000', '2', '60.00', b'0', null, null), ('16002', '上門獸醫', '16000', '2', '60.00', b'0', null, null), ('16003', '寵物訓練', '16000', '2', '60.00', b'0', null, null), ('16004', '寵物保姆', '16000', '2', '60.00', b'0', null, null), ('16005', '寵物用品', '16000', '2', '60.00', b'0', null, null), ('16006', '寵物善終', '16000', '2', '60.00', b'0', null, null), ('16007', '其他', '16000', '2', '60.00', b'0', null, null), ('17000', '美容美甲', '0', '1', '0.00', b'0', null, null), ('17001', '上門美容', '17000', '2', '70.00', b'0', null, null), ('17002', '上門美甲', '17000', '2', '70.00', b'0', null, null), ('17003', '上門化妝', '17000', '2', '70.00', b'0', null, null), ('17004', '其他', '17000', '2', '70.00', b'0', null, null), ('18000', '智能家居', '0', '1', '0.00', b'0', null, null), ('18001', '智能家庭系統', '18000', '2', '80.00', b'0', null, null), ('18002', '節能工程', '18000', '2', '80.00', b'0', null, null), ('18003', '防盜警報', '18000', '2', '80.00', b'0', null, null), ('18004', '遙距監控', '18000', '2', '80.00', b'0', null, null), ('18005', '其他', '18000', '2', '80.00', b'0', null, null), ('19000', '滅蟲服務', '0', '1', '0.00', b'0', null, null), ('19001', '白蟻防治', '19000', '2', '90.00', b'0', null, null), ('19002', '蟑螂防治', '19000', '2', '90.00', b'0', null, null), ('19003', '鼠類防治', '19000', '2', '90.00', b'0', null, null), ('19004', '除蟲除暪', '19000', '2', '90.00', b'0', null, null), ('19005', '其他', '19000', '2', '90.00', b'0', null, null), ('20000', '迷你倉', '0', '1', '0.00', b'0', null, null), ('20001', '文件', '20000', '2', '10.00', b'0', null, null), ('20002', '運動器材', '20000', '2', '10.00', b'0', null, null), ('20003', '紅酒', '20000', '2', '10.00', b'0', null, null), ('20004', '一般', '20000', '2', '10.00', b'0', null, null), ('20005', '其他', '20000', '2', '10.00', b'0', null, null), ('21000', '推拿按摩', '0', '1', '0.00', b'0', null, null), ('21001', '中醫推拿', '21000', '2', '20.00', b'0', null, null), ('21002', '泰式按摩', '21000', '2', '20.00', b'0', null, null), ('21003', '足底按摩', '21000', '2', '20.00', b'0', null, null), ('21004', '香薰推油', '21000', '2', '20.00', b'0', null, null), ('21005', '其他', '21000', '2', '20.00', b'0', null, null), ('22000', '家教補習', '0', '1', '0.00', b'0', null, null), ('22001', '小學', '22000', '2', '30.00', b'0', null, null), ('22002', '中學', '22000', '2', '30.00', b'0', null, null), ('22003', '專業外語', '22000', '2', '30.00', b'0', null, null), ('22004', '樂理樂器', '22000', '2', '30.00', b'0', null, null), ('22005', '體育運動', '22000', '2', '30.00', b'0', null, null), ('22006', '其他', '22000', '2', '30.00', b'0', null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
