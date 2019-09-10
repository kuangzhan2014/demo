SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_group` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '字典分组',
  `code` int(255) NOT NULL DEFAULT '0' COMMENT '字典编码',
  `code_label` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '编码值',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `parent_group` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '父分组',
  `parent_code` int(11) DEFAULT NULL COMMENT '父编码',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='数据字典';

-- ----------------------------
-- Table structure for sys_global_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_global_param`;
CREATE TABLE `sys_global_param` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_group` tinyint(4) NOT NULL,
  `param_name` varchar(100) NOT NULL,
  `param_key` varchar(50) NOT NULL,
  `param_value` varchar(200) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统全局配置';

-- ----------------------------
-- Table structure for sys_member
-- ----------------------------
DROP TABLE IF EXISTS `sys_member`;
CREATE TABLE `sys_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL,
  `member_name` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '用户姓名',
  `encrypted_password` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '加密密码',
  `password_salt` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码盐值',
  `cellphone` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `phone` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '电话号码',
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `real_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '真实姓名',
  `avatar` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后登录IP',
  `status` tinyint(255) NOT NULL DEFAULT '0' COMMENT '用户状态',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_member_name` (`member_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_member
-- ----------------------------
BEGIN;
INSERT INTO `sys_member` VALUES (1, NULL, 'admin', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', NULL, '13588031006', '333685', 'zhangchao51@163.com', '管理员', NULL, NULL, NULL, 1, '2017-08-26 14:39:23', '2018-06-06 00:14:19', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_member_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_member_organization`;
CREATE TABLE `sys_member_organization` (
  `member_id` bigint(20) NOT NULL,
  `organization_id` bigint(20) NOT NULL,
  PRIMARY KEY (`member_id`,`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户组织关联';

-- ----------------------------
-- Table structure for sys_member_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_member_role`;
CREATE TABLE `sys_member_role` (
  `member_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`member_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联';

-- ----------------------------
-- Records of sys_member_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_member_role` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '机构名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父机构ID',
  `parent_ids` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '所有父机构',
  `type` tinyint(255) NOT NULL DEFAULT '0' COMMENT '机构类型',
  `grade` tinyint(255) NOT NULL DEFAULT '0' COMMENT '机构级别',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '通用状态(0:禁止,1:正常)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统组织';

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限ID',
  `name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '权限名称',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型(1:目录,2:菜单,3:按钮)',
  `permission_value` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '权限值',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` tinyint(255) NOT NULL DEFAULT '0' COMMENT '预留，暂时无用',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='系统权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, NULL, '系统管理', 0, 'system:management', NULL, 0, '2017-11-12 13:56:11', '2017-11-12 14:04:08', NULL, NULL);
INSERT INTO `sys_permission` VALUES (2, 1, '用户管理', 0, 'system:member', NULL, 0, '2017-11-12 13:58:46', '2017-11-12 16:04:16', NULL, NULL);
INSERT INTO `sys_permission` VALUES (3, 1, '角色管理', 0, 'system:role', NULL, 0, '2017-11-12 14:08:43', '2017-11-12 16:04:22', NULL, NULL);
INSERT INTO `sys_permission` VALUES (4, 1, '权限管理', 0, 'system:permission', NULL, 0, '2017-11-12 16:10:35', '2017-11-12 16:10:41', NULL, NULL);
INSERT INTO `sys_permission` VALUES (5, 1, '参数设置', 0, 'system:param', NULL, 0, '2017-11-12 16:10:56', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (6, 1, '地区管理', 0, 'system:area', NULL, 0, '2017-11-12 16:10:56', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (7, 1, '字典管理', 0, 'system:dict', NULL, 0, '2017-11-12 16:10:56', NULL, NULL, NULL);
INSERT INTO `sys_permission` VALUES (8, 1, '缓存管理', 0, 'system:cache', NULL, 0, '2017-11-12 16:11:05', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `organization_id` bigint(20) DEFAULT NULL COMMENT '所属组织ID',
  `name` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '角色名称',
  `code` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '角色编码',
  `description` varchar(500) CHARACTER SET utf8 DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '预留，暂无用',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, NULL, '管理员', 'admin', '拥有系统所有权限', 0, '2017-11-12 13:38:36', '2018-07-11 23:26:17', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (1, 8);
COMMIT;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(50) NOT NULL COMMENT '名称',
  `area_short_name` varchar(50) DEFAULT NULL,
  `area_type` tinyint(4) NOT NULL COMMENT '类型（国家、省份直辖市、地市、区县）',
  `area_code` varchar(12) NOT NULL COMMENT '编号',
  `area_pinyin` varchar(200) DEFAULT NULL COMMENT '拼音',
  `area_jianpin` varchar(50) DEFAULT NULL COMMENT '简拼',
  `area_first_char` varchar(10) DEFAULT NULL COMMENT '首字母',
  `area_lng` decimal(10,7) DEFAULT NULL COMMENT '经度',
  `area_lat` decimal(10,7) DEFAULT NULL COMMENT '纬度',
  `city_code` varchar(10) DEFAULT NULL COMMENT '城市区号',
  `zip_code` varchar(10) DEFAULT NULL COMMENT '邮政编码',
  `parent_code` varchar(12) DEFAULT NULL COMMENT '父编号',
  `parent_codes` varchar(1000) DEFAULT NULL COMMENT '所有父编号',
  `tree_sort` bigint(20) DEFAULT NULL COMMENT '本级排序号',
  `tree_sorts` varchar(1000) DEFAULT NULL COMMENT '所有级别排序号',
  `tree_leaf` tinyint(4) DEFAULT NULL COMMENT '是否末级',
  `tree_level` tinyint(4) DEFAULT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) DEFAULT NULL COMMENT '全节点名',
  `remark` varchar(200) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_by` bigint(20) DEFAULT NULL,
  `update_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='行政区';

-- ----------------------------
-- Table structure for uc_persistent_token
-- ----------------------------
DROP TABLE IF EXISTS `uc_persistent_token`;
CREATE TABLE `uc_persistent_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `token_value` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ip_address` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `user_agent` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `login` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_token_value` (`token_value`),
  KEY `fk_uc_persistent_token_uc_user1_idx` (`user_id`),
  CONSTRAINT `fk_uc_persistent_token_uc_user1` FOREIGN KEY (`user_id`) REFERENCES `uc_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='持久化Token';

-- ----------------------------
-- Table structure for uc_user
-- ----------------------------
DROP TABLE IF EXISTS `uc_user`;
CREATE TABLE `uc_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户',
  `login_name` varchar(50) NOT NULL COMMENT '用户名',
  `cellphone` varchar(50) DEFAULT NULL COMMENT '手机号码',
  `encrypted_password` varchar(50) DEFAULT NULL COMMENT '加密密码',
  `password_salt` varchar(50) DEFAULT NULL COMMENT '密码盐值',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真名',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_date` varchar(45) DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Table structure for uc_user_oauth
-- ----------------------------
DROP TABLE IF EXISTS `uc_user_oauth`;
CREATE TABLE `uc_user_oauth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户第三方登录授权',
  `user_id` bigint(20) DEFAULT NULL,
  `oauth_provider` tinyint(1) NOT NULL COMMENT 'oauth授权方',
  `open_id` varchar(50) NOT NULL COMMENT 'OpenID',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别（1男，2女）',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `country` varchar(100) DEFAULT NULL COMMENT '国家',
  `avatar_url` varchar(200) DEFAULT NULL COMMENT '头像地址',
  `union_id` varchar(100) DEFAULT NULL COMMENT '微信UnionId',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态（绑定、解绑）',
  `create_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_uc_user_oauth_uc_user1_idx` (`user_id`),
  CONSTRAINT `fk_uc_user_oauth_uc_user1` FOREIGN KEY (`user_id`) REFERENCES `uc_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户第三方登录授权';


SET FOREIGN_KEY_CHECKS = 1;

