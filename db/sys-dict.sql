DELETE FROM sys_dict;
ALTER TABLE sys_dict AUTO_INCREMENT = 1;

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('common_status', '1', '可用', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('common_status', '9', '不可用', '9', null, null, now());

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('member_status', '1', '正常', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('member_status', '9', '锁定', '9', null, null, now());

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('param_group', '1', '系统', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('param_group', '9', '自定义', '9', null, null, now());

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('area_type', '0', '国家', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('area_type', '1', '省份直辖市', '9', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('area_type', '2', '地市', '9', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('area_type', '3', '区县', '9', null, null, now());

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('user_sex', '0', '保密', '0', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('user_sex', '1', '男', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('user_sex', '2', '女', '2', null, null, now());

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('user_status', '1', '正常', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('user_status', '9', '锁定', '9', null, null, now());

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('oauth_provider', '1', '微信', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('oauth_provider', '2', 'QQ', '2', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('oauth_provider', '3', '微博', '3', null, null, now());

INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('user_oauth_status', '1', '绑定', '1', null, null, now());
INSERT INTO `sys_dict`(dict_group, code, code_label, sort, parent_group, parent_code, create_date)
VALUES ('user_oauth_status', '2', '解绑', '2', null, null, now());