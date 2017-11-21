/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : dc

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-08-25 10:43:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `channel`
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(128) NOT NULL,
  `group_id` varchar(20) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `user_count` int(11) DEFAULT NULL,
  `record_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('107', 'C18742', '1001', 'one', '29', '2017-07-25 10:38:21');
INSERT INTO `channel` VALUES ('108', 'C18743', '1001', 'two', '11', '2017-07-25 10:38:21');
INSERT INTO `channel` VALUES ('109', 'C18750', '1001', '1234', '6', '2017-07-25 10:38:21');

-- ----------------------------
-- Table structure for `channel_member`
-- ----------------------------
DROP TABLE IF EXISTS `channel_member`;
CREATE TABLE `channel_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(128) DEFAULT NULL COMMENT '频道Id',
  `user_id` varchar(128) DEFAULT NULL,
  `level` int(11) DEFAULT NULL COMMENT '对讲级别',
  `is_master` tinyint(4) DEFAULT NULL COMMENT '是否是主用户：1是  0不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1745 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_member
-- ----------------------------
INSERT INTO `channel_member` VALUES ('1348', 'C18742', '100110026', '40', '0');
INSERT INTO `channel_member` VALUES ('1598', 'C18742', '100110025', '40', '0');
INSERT INTO `channel_member` VALUES ('1599', 'C18743', '100110025', '40', '0');
INSERT INTO `channel_member` VALUES ('1600', 'C18750', '100110025', '40', '0');
INSERT INTO `channel_member` VALUES ('1604', 'C18742', '100110024', '40', '0');
INSERT INTO `channel_member` VALUES ('1605', 'C18743', '100110024', '40', '0');
INSERT INTO `channel_member` VALUES ('1606', 'C18750', '100110024', '40', '0');
INSERT INTO `channel_member` VALUES ('1739', 'C18742', '100110021', '10', '1');
INSERT INTO `channel_member` VALUES ('1740', 'C18743', '100110021', '40', '1');
INSERT INTO `channel_member` VALUES ('1741', 'C18750', '100110021', '40', '0');
INSERT INTO `channel_member` VALUES ('1742', 'C18742', '100110022', '50', '0');
INSERT INTO `channel_member` VALUES ('1743', 'C18743', '100110022', '40', '0');
INSERT INTO `channel_member` VALUES ('1744', 'C18750', '100110022', '40', '1');

-- ----------------------------
-- Table structure for `session_key`
-- ----------------------------
DROP TABLE IF EXISTS `session_key`;
CREATE TABLE `session_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(128) DEFAULT NULL COMMENT '用户ID',
  `channel_id` varchar(128) DEFAULT NULL COMMENT '频道ID',
  `channel_type` varchar(10) DEFAULT '1' COMMENT '频道类型：1系统频道  2临时频道',
  `session_key` varchar(512) DEFAULT NULL COMMENT '会话秘钥',
  `key_flag` varchar(256) DEFAULT '0' COMMENT '会话秘钥版明文唯一标识',
  `record_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of session_key
-- ----------------------------
INSERT INTO `session_key` VALUES ('247', '100110022', 'C18750', '1', 'girPlLw9IQCXgOKi9jzYT0AlU08IKjUXp5dnErYBlziVZir8Q2dkntXYazdoqTIiei29onByG7moHG3xrhL59GAOY7ZRvdvGQfikm+tgCEOaxifo2KlGjVmCXjqxBtoqN/XmtFLDHYACvVQpfDUJ4LyR7OlZwNv7e3mkKOjtQGE=', 'C18750_1500950328348', '2017-07-25 10:38:50');
INSERT INTO `session_key` VALUES ('249', '100110022', 'C18750', '1', 'bIDHkCUFwgEdVV3klgrfv1wVu9bf9VP6RpYeBGugH8v9AIkbOZ9LWLbrdYCJz8BBOYtj5N2j86Can7SQhgc/IO5roheDgpEUBwBHg6+5PgmxeH83YEgr1rwpsYjmY16/bghjIvZCImg6nXwSVGw3QAwnbvW7VncJYjH0hXv1x3Y=', 'C18750_1500950328166', '2017-07-25 10:38:50');
INSERT INTO `session_key` VALUES ('250', '100110022', 'C18742', '1', 'f39xH75oRXlhGxI3ZDv/0er5JxJ+Deh5f2+YtJtV5fYfwMTLNUqdr2e/HG3MT2Xf2if/f3gq5/4l8TF1KIg+8kOvc8U3JI84gmLLfYSCAcN8rgiv2DRR1q29LavxuCa4KrpCG9B+esuv812CS+B7G/5x/Q+BHnKfqrU9s59S2Lw=', 'C18742_1502162153660', '2017-08-08 11:15:56');
INSERT INTO `session_key` VALUES ('251', '100110022', 'C18743', '1', 'JrkZwzxidyHPH/bZ4OVreFmk9kn+twIJLLF+a2AZcAfW/tCscZkUNnHDXqfXTpPGAvJY9fE8CbdfzSJEzxDT4zlHFzufLvf07u73aP4growrVNYq2QUiRNefYerrD4X73KXSIey/bVIzgioVcGQGyqGjDkE2B+WDINLlcQw2tGA=', 'C18743_1502162154113', '2017-08-08 11:15:56');
INSERT INTO `session_key` VALUES ('261', '100110021', 'C18742', '1', 'f39xH75oRXlhGxI3ZDv/0er5JxJ+Deh5f2+YtJtV5fYfwMTLNUqdr2e/HG3MT2Xf2if/f3gq5/4l8TF1KIg+8kOvc8U3JI84gmLLfYSCAcN8rgiv2DRR1q29LavxuCa4KrpCG9B+esuv812CS+B7G/5x/Q+BHnKfqrU9s59S2Lw=', 'C18742_1502162153660', '2017-08-08 11:15:56');
INSERT INTO `session_key` VALUES ('262', '100110021', 'C18743', '1', 'JrkZwzxidyHPH/bZ4OVreFmk9kn+twIJLLF+a2AZcAfW/tCscZkUNnHDXqfXTpPGAvJY9fE8CbdfzSJEzxDT4zlHFzufLvf07u73aP4growrVNYq2QUiRNefYerrD4X73KXSIey/bVIzgioVcGQGyqGjDkE2B+WDINLlcQw2tGA=', 'C18743_1502162154113', '2017-08-08 11:15:56');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` varchar(128) NOT NULL COMMENT '用户ID',
  `username` varchar(20) DEFAULT NULL COMMENT '登录名',
  `name` varchar(20) DEFAULT NULL COMMENT '用户名称',
  `group_id` varchar(20) DEFAULT NULL COMMENT '企业ID',
  `record_time` datetime DEFAULT NULL,
  `public_key` varchar(512) DEFAULT NULL COMMENT '用户公钥',
  `key_status` tinyint(4) DEFAULT '0' COMMENT '用户获取公钥状态：1已获取 0未获取',
  `record_key_time` datetime DEFAULT NULL COMMENT '记录公钥的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('80', '100110021', 'shaxiaoshuang', '沙晓双', '1001', '2017-07-25 10:38:20', 'k4P/7ap7a9SmWlGmRJZuQcbl5UfCDd3kJ088xiNeN9Ft10Muvz63JqDc7vV0JJ6SN4KXUIQfv6dVe2CwWdpHAGZ8ILOw/6dJX/q3wQQG2tQ7ebdvbZTqZMgX2pqc+tVhJsDdy9YYq01qu6QKSLOwsjpneNlLXj6g4pxO9qc3Was=', '0', '2017-08-08 11:15:55');
INSERT INTO `user` VALUES ('81', '100110022', 'suntaiming', '孙太明', '1001', '2017-07-25 10:38:47', 'k4P/7ap7a9SmWlGmRJZuQcbl5UfCDd3kJ088xiNeN9Ft10Muvz63JqDc7vV0JJ6SN4KXUIQfv6dVe2CwWdpHAGZ8ILOw/6dJX/q3wQQG2tQ7ebdvbZTqZMgX2pqc+tVhJsDdy9YYq01qu6QKSLOwsjpneNlLXj6g4pxO9qc3Was=', '0', '2017-07-25 10:38:49');
INSERT INTO `user` VALUES ('82', '100110026', 'yuwenlei', '于文磊', '1001', '2017-07-25 10:53:41', null, '0', null);
INSERT INTO `user` VALUES ('83', '100110024', 'zhaojunhu', '赵军虎', '1001', '2017-07-25 10:53:52', null, '0', null);
INSERT INTO `user` VALUES ('84', '100110025', 'jinmeng', '靳猛', '1001', '2017-07-25 16:27:12', null, '0', null);
INSERT INTO `user` VALUES ('85', '100110017', '100110017', '100110017', '1001', '2017-08-09 16:24:57', null, '0', null);
