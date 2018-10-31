/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : icdatabase

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2018-10-31 19:25:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ic_user
-- ----------------------------
DROP TABLE IF EXISTS `ic_user`;
CREATE TABLE `ic_user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(30) DEFAULT NULL,
  `user_pass` varchar(65) DEFAULT NULL,
  `user_phone` varchar(30) DEFAULT NULL,
  `is_admin` int(2) DEFAULT NULL,
  `register_time` varchar(30) DEFAULT NULL,
  `last_login_time` varchar(30) DEFAULT NULL,
  `last_login_ip` varchar(60) DEFAULT NULL,
  `user_guid` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `user_status` int(11) DEFAULT '1' COMMENT '1：正常；\r\n2：密码错误此时过多暂时冻结；\r\n3：永久冻结；\r\n4：已注销。',
  `user_state` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_verify` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `department_id` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ic_user
-- ----------------------------
INSERT INTO `ic_user` VALUES ('1', 'admin', 'e4ac42bb944b58ece00217ce0db33fd36fa159ad310e4c7693b016b92e14603e', '18285025487', '1', null, null, null, '7893a2cf-8d04-49f9-8905-a608d02ff53d,7893a2cf8d0449f98905a608d02ff53d', null, '1', 'firstPwd', 'OTkyMzAyMTU0MDk4MjQ1MDMzMA==');
INSERT INTO `ic_user` VALUES ('2', '刘德华', 'e4ac42bb944b58ece00217ce0db33fd36fa159ad310e4c7693b016b92e14603e', '15885562249', '2', null, null, null, null, '1', '1', 'firstPwd', 'ODcyMzMxMTU0MDk4MTYzNzkwNA==');
INSERT INTO `ic_user` VALUES ('4', '张XX', 'e4ac42bb944b58ece00217ce0db33fd36fa159ad310e4c7693b016b92e14603e', '18285025481', '2', null, null, null, null, null, '2', 'firstPwd', 'NzQyMzA4MTU0MDk4MjYwNTY3NA==');
INSERT INTO `ic_user` VALUES ('5', '周杰伦', 'e4ac42bb944b58ece00217ce0db33fd36fa159ad310e4c7693b016b92e14603e', '18285025483', '2', null, null, null, null, null, '1', null, null);
