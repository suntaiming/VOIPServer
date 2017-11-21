/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : voipdb

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-11-21 17:37:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `contacts`
-- ----------------------------
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contacts_id` varchar(128) NOT NULL COMMENT '联系人id',
  `relation_id` varchar(128) DEFAULT NULL COMMENT '好友关系ID',
  `master_phone` varchar(20) NOT NULL COMMENT '主用户手机号',
  `friend_phone` varchar(20) NOT NULL COMMENT '联系人手机号',
  `name` varchar(50) DEFAULT NULL COMMENT '联系人名字',
  `company` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL COMMENT '联系人邮箱',
  `head_image` varchar(200) DEFAULT NULL COMMENT '联系人头像',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `record_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `contacts_id` (`contacts_id`),
  KEY `relation_id` (`relation_id`) USING BTREE,
  KEY `master_phone` (`master_phone`) USING BTREE,
  KEY `contacts_user_pk_3` (`friend_phone`),
  CONSTRAINT `contacts_relation_pk_1` FOREIGN KEY (`relation_id`) REFERENCES `contacts_relation` (`relation_id`) ON DELETE CASCADE,
  CONSTRAINT `contacts_user_pk_2` FOREIGN KEY (`master_phone`) REFERENCES `user` (`phone_number`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contacts_user_pk_3` FOREIGN KEY (`friend_phone`) REFERENCES `user` (`phone_number`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contacts
-- ----------------------------

-- ----------------------------
-- Table structure for `contacts_relation`
-- ----------------------------
DROP TABLE IF EXISTS `contacts_relation`;
CREATE TABLE `contacts_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `relation_id` varchar(128) NOT NULL COMMENT '好友关系id',
  `from_phone` varchar(20) NOT NULL COMMENT '申请人手机号',
  `to_phone` varchar(20) NOT NULL COMMENT '被申请人手机号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '关系状态：0待通过  1通过  2拒绝  3好友删除 ',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `record_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `relation_id_2` (`relation_id`),
  KEY `from_phone` (`from_phone`) USING BTREE,
  KEY `to_phone` (`to_phone`) USING BTREE,
  KEY `relation_id` (`relation_id`) USING BTREE,
  CONSTRAINT `relation_user_pk_1` FOREIGN KEY (`from_phone`) REFERENCES `user` (`phone_number`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `relation_user_pk_2` FOREIGN KEY (`to_phone`) REFERENCES `user` (`phone_number`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contacts_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `contacts_verify`
-- ----------------------------
DROP TABLE IF EXISTS `contacts_verify`;
CREATE TABLE `contacts_verify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `verify_id` varchar(128) NOT NULL COMMENT '新好友验证记录id',
  `relation_id` varchar(128) NOT NULL COMMENT '关系ID',
  `master_phone` varchar(20) NOT NULL COMMENT '主用户手机号',
  `friend_phone` varchar(20) NOT NULL COMMENT '好友手机号',
  `name` varchar(50) DEFAULT NULL COMMENT '好友备注名称',
  `remark` varchar(215) DEFAULT NULL COMMENT '申请验证信息',
  `type` tinyint(4) NOT NULL COMMENT '类型： 1 申请    2被申请',
  `status` tinyint(4) NOT NULL COMMENT '状态：0待通过  1 通过  2 拒绝  3超时（需要计算得出，数据库中无此状态）',
  `modify_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `verify_id` (`verify_id`),
  KEY `relation_id` (`relation_id`),
  CONSTRAINT `verify_relation_` FOREIGN KEY (`relation_id`) REFERENCES `contacts_relation` (`relation_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contacts_verify
-- ----------------------------

-- ----------------------------
-- Table structure for `c_sub_service`
-- ----------------------------
DROP TABLE IF EXISTS `c_sub_service`;
CREATE TABLE `c_sub_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `service_id` varchar(128) NOT NULL COMMENT '服务注册ID',
  `service_secret` varchar(128) NOT NULL,
  `logout_url` varchar(256) DEFAULT NULL COMMENT '退出登录接口地址（认证中心回调）',
  `logout_all` tinyint(4) DEFAULT NULL COMMENT '用户在该服务退出登录是是否退出全局会话：0否 1是 默认否',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`,`service_id`),
  UNIQUE KEY `service_id` (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_sub_service
-- ----------------------------
INSERT INTO `c_sub_service` VALUES ('22', 'VOIP', 'VOIPxindun123', '', '1', '2017-11-15 14:32:38', '2017-11-15 14:32:38');
INSERT INTO `c_sub_service` VALUES ('23', 'Pinyinime', 'Pinyinimexindun123', '', '1', '2017-11-15 14:32:38', '2017-11-15 14:32:38');
INSERT INTO `c_sub_service` VALUES ('24', 'SafetyPlatfrom', 'SafetyPlatfromxindun123', '', '1', '2017-11-15 14:32:38', '2017-11-15 14:32:38');
INSERT INTO `c_sub_service` VALUES ('25', 'POC', 'POCxindun123', '', '1', '2017-11-15 14:32:38', '2017-11-15 14:32:38');

-- ----------------------------
-- Table structure for `c_user`
-- ----------------------------
DROP TABLE IF EXISTS `c_user`;
CREATE TABLE `c_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(128) NOT NULL COMMENT '用户ID',
  `username` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(128) NOT NULL,
  `phone_number` varchar(12) DEFAULT NULL COMMENT '手机号',
  `service_id` varchar(128) DEFAULT NULL COMMENT '子服务注册ID',
  `sn` varchar(256) DEFAULT NULL COMMENT '加密设备SN号',
  `status` tinyint(4) DEFAULT '1' COMMENT '用户状态:0停用  1正常  默认正常',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `user_id_2` (`user_id`),
  UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of c_user
-- ----------------------------
INSERT INTO `c_user` VALUES ('1', 'e79d59e3297c4e7b83b74732c9aee168', '13311083453', '123', '13311083453', null, 'qwertyuiop', '1', '2017-10-31 14:16:24');

-- ----------------------------
-- Table structure for `sms`
-- ----------------------------
DROP TABLE IF EXISTS `sms`;
CREATE TABLE `sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `phone_number` varchar(20) NOT NULL COMMENT '手机号',
  `verify_code` varchar(20) DEFAULT NULL COMMENT '验证码',
  `time` bigint(20) DEFAULT NULL COMMENT '时间long型',
  `record_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sms
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(128) DEFAULT NULL COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '账号',
  `password` varchar(120) DEFAULT NULL COMMENT '密码',
  `phone_number` varchar(11) NOT NULL COMMENT '手机号',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别：1男 2女',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `public_key` varchar(512) DEFAULT NULL COMMENT '公钥',
  `key_sequence` varchar(128) DEFAULT '0' COMMENT '公钥序数',
  `contacts_sequence` varchar(128) DEFAULT '0' COMMENT '联系人更新序数',
  `company` varchar(100) DEFAULT NULL COMMENT '公司名臣',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` tinyint(4) DEFAULT NULL COMMENT '用户状态：1正常 2停用',
  `head_image` varchar(256) DEFAULT NULL COMMENT '头像',
  `client_id` varchar(128) DEFAULT NULL COMMENT '用户个推clientId',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `record_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_number_2` (`phone_number`),
  KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '5511b45e73624d6db53c7d5cd866590c', '13311083453', 'e10adc3949ba59abbe56e057f20f883e', '13311083453', '0', '0', null, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXo6ZWlRgMR5A5zxwfUULpTsg+1hChqtdTmaSfAwbfQFEtmCi9/0Ec1ZlVz4Pj4TFYsHQpYz0pVYgTnr7oF9KjVNNPzgEOCi4QxIxXGNv8eESyB63Isw6WOsbKj8wBAKdrLvFcdmUf+VBZlxM5uUWKdi4AXsl3PiC8kNRNfPhQAwIDAQAB', '93a1d2b6fcf6a4df3c9327c4210b465a', 'd3ceca60b61745db8690f4ac652df017', null, null, '1', null, '16ef39922d68edbf2c93671f13bac5c5', '2017-11-08 14:33:25', '2017-09-01 11:07:21');
INSERT INTO `user` VALUES ('2', '14d2acc5fbd0445fa5d535b8deae5764', '18410439015', 'e10adc3949ba59abbe56e057f20f883e', '18410439015', '0', '0', null, null, '0', '0', null, null, '1', null, null, '2017-09-18 10:20:21', '2017-09-08 17:42:46');
INSERT INTO `user` VALUES ('3', 'dc2ff1f213e84eb194fc5cd9af47a009', '18910627371', '200820e3227815ed1756a6b531e7e0d2', '18910627371', '0', '0', null, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxNwCnRpfSgnBjWcUJnHD2R/BSS5/HCMnobpm+FUIOWg9GGT6UmM0MBJRUidQ1FU+ab8J785UeVZUPPAIuQz8CjF8Q86KLtUviScqCgTsB95CaUmc5ZyZw/lB/QfARxhLu2dE5YeZbayQO9vHyxDGjViU6xE1gXIR4dZZz/3QjIQIDAQAB', '3f52caed5d1693356f0ce7deb664a0c9', '790876d4ef144ca291c1f8d7af6fd4e1', null, null, '1', null, '9bbeef261f54292570f965b8ba70af65', '2017-11-08 16:53:14', '2017-09-12 17:55:41');
INSERT INTO `user` VALUES ('4', 'cdba09eb7c774295bd1f2538c07f4f62', '15103843410', 'e10adc3949ba59abbe56e057f20f883e', '15103843410', '0', '0', null, null, '0', '0', null, null, '1', null, null, '2017-09-20 10:05:08', '2017-09-20 10:05:08');
INSERT INTO `user` VALUES ('5', '904d607ab0d44b908a476874502bc655', '18322717305', 'e10adc3949ba59abbe56e057f20f883e', '18322717305', '0', '0', null, '23c4b1a537d9882f83243b8b1efe7018', 'cb3e041515b07b140eb86ed200bfa9bb', '2350bd67e4504b0aad5bb7d7b03765b6', null, null, '1', null, null, '2017-10-10 15:17:52', '2017-09-21 17:08:34');
INSERT INTO `user` VALUES ('6', 'd0b650ca02bc40829030f93f461a4d73', '15110055185', 'e10adc3949ba59abbe56e057f20f883e', '15110055185', '0', '0', null, '23c4b1a537d9882f83243b8b1efe7018', 'cb3e041515b07b140eb86ed200bfa9bb', 'aba60534a3fd4f90b1b3fac5fd210b7d', null, null, '1', null, null, '2017-09-27 13:21:39', '2017-09-27 13:21:05');
INSERT INTO `user` VALUES ('7', '1c8a950f22f349f5a68b5777aa0b892e', '17319227746', 'e10adc3949ba59abbe56e057f20f883e', '17319227746', '0', '0', null, null, '0', '0', null, null, '1', null, null, '2017-09-27 13:36:28', '2017-09-27 13:36:28');
INSERT INTO `user` VALUES ('8', 'eee2084bef094a899a85cd7213078a59', '15901339402', 'e10adc3949ba59abbe56e057f20f883e', '15901339402', '0', '0', null, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDjv3MRgvFQqdWp9fz+vOyHh/lR15aKwzH0oy/kJp7CJdpXevOqMvNiKTVlqFA8NRHWK8icQ6E+b47Fk+34zzSqQI2vonr00t94s1xrUm+IPKHpv0u+mohb8vcQM1I1/av8ZbOj6945uZhK2LVG6udfobVC4+CnS+To76isV4maHwIDAQAB', '9c362c14ac424d735cea00ca39c9c6b4', 'aba60534a3fd4f90b1b3fac5fd210b7d', null, null, '1', null, null, '2017-10-13 15:37:36', '2017-09-28 09:53:33');
INSERT INTO `user` VALUES ('9', '68198c6393234885a106838ab50f59ec', '15538322852', '200820e3227815ed1756a6b531e7e0d2', '15538322852', '0', '0', null, 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+LuAQdDC+hAlTfAA8fb5HwrLmRBge8iH4sQ7lsKDaH0sluEsmTdkh0SG7dgMdlKTsUlg6AyQnHtfZxeQlnyziWjR0PPRKFGVDZ/qeNyQSVi5t4EeLVitG3GcNEOp+E6eIYQp5VPj2qsk02ZBkTXS0B839fpw66wUUqzp57W6rwwIDAQAB', '18111536783be692fddf4439283edbea', '0bda5994468b49f38344547af8545ba8', null, null, '1', null, null, '2017-10-17 10:21:50', '2017-10-16 14:11:30');
