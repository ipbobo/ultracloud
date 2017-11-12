delete from sys_menu where menu_id in(121, 122, 123);
INSERT INTO `sys_menu` VALUES ('121', '申请管理', '#', '0', '2', 'menu-icon fa fa-home blue', '2', '1');
INSERT INTO `sys_menu` VALUES ('122', '资源申请', 'appMgrPre.do', '121', '1', 'menu-icon fa fa-home blue', '1', '1');
INSERT INTO `sys_menu` VALUES ('123', '运维服务申请', 'appMgrPre.do', '121', '2', 'menu-icon fa fa-home blue', '1', '1');