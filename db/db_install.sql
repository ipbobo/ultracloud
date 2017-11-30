
-- ----------------------------
-- 环境
-- ----------------------------
DROP TABLE IF EXISTS `t_environment`;
CREATE TABLE `t_environment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `datacenter_id` bigint unsigned DEFAULT NULL COMMENT '数据中心id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_datacenter_id(`datacenter_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='环境'; 

-- ----------------------------
-- 云平台
-- ----------------------------
DROP TABLE IF EXISTS `t_cloudplatform`;
CREATE TABLE `t_cloudplatform` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL COMMENT '名称',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云平台'; 

-- ----------------------------
-- 数据中心
-- ----------------------------
DROP TABLE IF EXISTS `t_datacenter`;
CREATE TABLE `t_datacenter` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `cpf_id` bigint unsigned NOT NULL COMMENT '云平台id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_cpf_id(`cpf_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据中心'; 

-- ----------------------------
-- 集群
-- ----------------------------
DROP TABLE IF EXISTS `t_cluster`;
CREATE TABLE `t_cluster` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `datacenter_id` bigint unsigned DEFAULT NULL COMMENT '数据中心id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_datacenter_id(`datacenter_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='集群'; 

-- ----------------------------
-- 宿主机
-- ----------------------------
DROP TABLE IF EXISTS `t_hostmachine`;
CREATE TABLE `t_hostmachine` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `type` tinyint unsigned NOT NULL COMMENT '类型',
  `status` tinyint unsigned NOT NULL COMMENT '状态',
  `ip` varchar(20) DEFAULT NULL COMMENT '宿主机ip',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `localdisk` int unsigned DEFAULT NULL COMMENT '本地磁盘',
  `devicenum` varchar(30) DEFAULT NULL COMMENT '设备号',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_type(`type`),
  INDEX idx_status(`status`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='宿主机'; 

-- ----------------------------
-- 存储
-- ----------------------------
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `type` tinyint unsigned NOT NULL COMMENT '类型',
  `allspace` int unsigned DEFAULT NULL COMMENT '所有空间',
  `freespace` int unsigned DEFAULT NULL COMMENT '可用空间',
  `threshold` tinyint unsigned NOT NULL COMMENT '阈值',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_type(`type`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储'; 


-- ----------------------------
-- 存储与宿主机关联
-- ----------------------------
DROP TABLE IF EXISTS `t_storage_hm_map`;
CREATE TABLE `t_storage_hm_map` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `storage_id` bigint unsigned NOT NULL COMMENT '存储id',
  `hostmachine_id` bigint unsigned NOT NULL COMMENT '宿主机id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  UNIQUE INDEX uk_storage_hostmachine(`storage_id`,`hostmachine_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储与宿主机关联'; 

-- ----------------------------
-- 网络
-- ----------------------------
DROP TABLE IF EXISTS `t_network`;
CREATE TABLE `t_network` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `ippool` varchar(40) DEFAULT NULL COMMENT 'ip池',
  `mask` varchar(20) DEFAULT NULL COMMENT '掩码',
  `gateway` varchar(20) DEFAULT NULL COMMENT '网关',
  `dns1` varchar(20) DEFAULT NULL COMMENT 'DNS1',
  `dns2` varchar(20) DEFAULT NULL COMMENT 'DNS2',
  `totalip` int unsigned DEFAULT NULL COMMENT 'ip总数',
  `freeip` int unsigned DEFAULT NULL COMMENT 'ip剩余量',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网络'; 

-- ----------------------------
-- 数据中心与网络关联
-- ----------------------------
DROP TABLE IF EXISTS `t_datacenter_network_map`;
CREATE TABLE `t_datacenter_network_map` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `datacenter_id` bigint unsigned NOT NULL COMMENT '数据中心id',
  `network_id` bigint unsigned NOT NULL COMMENT '网络id',
  `ippool` varchar(40) DEFAULT NULL COMMENT 'ip池',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_datacenter_id(`datacenter_id`),
  INDEX idx_network_id(`network_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据中心与网络关联'; 

-- ----------------------------
-- 虚拟机
-- ----------------------------
DROP TABLE IF EXISTS `t_virtualmachine`;
CREATE TABLE `t_virtualmachine` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '数据盘',
  `status` tinyint unsigned NOT NULL COMMENT '状态',
  `hostmachine_id` bigint unsigned NOT NULL COMMENT '宿主机id',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台',
  `os` varchar(20) DEFAULT NULL COMMENT '操作系统',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_status(`status`),
  INDEX idx_hostmachine_id(`hostmachine_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟机'; 


-- ----------------------------
-- 快照
-- ----------------------------
DROP TABLE IF EXISTS `t_snapshoot`;
CREATE TABLE `t_snapshoot` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `virtualmachine_id` bigint unsigned NOT NULL COMMENT '虚拟机id',
  `creator` bigint unsigned NOT NULL COMMENT '创建者id',
  `url` varchar(1000) DEFAULT NULL COMMENT '快照地址',
  `detail` varchar(500) DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_creator(`creator`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快照'; 

-- ----------------------------
-- 镜像
-- ----------------------------
DROP TABLE IF EXISTS `t_mirror`;
CREATE TABLE `t_mirror` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `ostype` varchar(20) DEFAULT NULL COMMENT '操作系统类型',
  `osname` varchar(20) DEFAULT NULL COMMENT '操作系统名称',
  `bitrate` tinyint unsigned DEFAULT NULL COMMENT '位数',
  `creator` bigint unsigned NOT NULL COMMENT '创建者id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_creator(`creator`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='镜像'; 

-- ----------------------------
-- 镜像模板
-- ----------------------------
DROP TABLE IF EXISTS `t_mirrortemplate`;
CREATE TABLE `t_mirrortemplate` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `url` varchar(1000) DEFAULT NULL COMMENT '镜像模板文件存储路径',
  `size` int unsigned DEFAULT NULL COMMENT '文件大小',
  `datacenter_id` bigint unsigned NOT NULL COMMENT '数据中心id',
  `resourcepool_id` bigint unsigned NOT NULL COMMENT '资源池id',
  `ostype` varchar(20) DEFAULT NULL COMMENT '操作系统类型',
  `osname` varchar(20) DEFAULT NULL COMMENT '操作系统名称',
  `bitrate` tinyint unsigned DEFAULT NULL COMMENT '位数',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_datacenter_id(`datacenter_id`),
  INDEX idx_resourcepool_id(`resourcepool_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='镜像模板'; 

-- ----------------------------
-- 镜像与模板关联
-- ----------------------------
DROP TABLE IF EXISTS `t_mirror_template_map`;
CREATE TABLE `t_mirror_template_map` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `mirror_id` bigint unsigned NOT NULL COMMENT '镜像id',
  `mirrortemplate_id` bigint unsigned NOT NULL COMMENT '镜像模板id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  UNIQUE INDEX uk_mirror_template(`mirror_id`,`mirrortemplate_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='镜像与模板关联'; 

-- ----------------------------
-- 工单
-- ----------------------------
DROP TABLE IF EXISTS `t_workorder`;
CREATE TABLE `t_workorder` (
  `id` bigint unsigned NOT NULL,
  `username` varchar(40) NOT NULL COMMENT '申请者id',
  `status` tinyint unsigned NOT NULL COMMENT '状态',
  `areacode` varchar(20) DEFAULT NULL COMMENT '地域',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '数据盘',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台',
  `os` varchar(20) DEFAULT NULL COMMENT '操作系统',
  `bitrate` tinyint unsigned DEFAULT NULL COMMENT '位数',
  `mirrortemplate_id` bigint unsigned NOT NULL COMMENT '镜像模板id',
  `virtualmachine_num` int unsigned DEFAULT NULL COMMENT '虚拟机数量',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_username(`username`),
  INDEX idx_status(`status`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单'; 


-- ----------------------------
-- 订单
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint unsigned NOT NULL,
  `username` varchar(40) NOT NULL COMMENT '申请者id',
  `status` tinyint unsigned NOT NULL COMMENT '状态',
  `areacode` varchar(20) DEFAULT NULL COMMENT '地域',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '数据盘',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台',
  `os` varchar(20) DEFAULT NULL COMMENT '操作系统',
  `bitrate` tinyint unsigned DEFAULT NULL COMMENT '位数',
  `mirrortemplate_id` bigint unsigned NOT NULL COMMENT '镜像模板id',
  `virtualmachine_num` int unsigned DEFAULT NULL COMMENT '虚拟机数量',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_username(`username`),
  INDEX idx_status(`status`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单'; 


-- ----------------------------
-- 项目
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `id` varchar(32) NOT NULL COMMENT '用户id',
  `name` varchar(20) NOT NULL COMMENT '项目名称',
  `shortname` varchar(20) DEFAULT NULL COMMENT '项目简称',
  `level` varchar(20) DEFAULT NULL COMMENT '项目等级',
  `parent_id` bigint unsigned DEFAULT NULL COMMENT '父项目id',
  `DEPARTMENT_ID` varchar(40) DEFAULT NULL COMMENT '项目所归属部门',
  `USERNAME` varchar(40) DEFAULT NULL COMMENT '负责人',
  `usergroup_id` bigint unsigned DEFAULT NULL COMMENT '审核用户组id',
  `detail` varchar(500) DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_level(`level`),
  INDEX idx_parent_idd(`parent_id`),
  INDEX idx_DEPARTMENT_ID(`DEPARTMENT_ID`),
  INDEX idx_USERNAME(`USERNAME`),
  INDEX idx_usergroup_id(`usergroup_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目'; 

-- ----------------------------
-- 项目用户关联
-- ----------------------------
DROP TABLE IF EXISTS `t_project_user_map`;
create table `t_project_user_map` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `project_id` varchar(32) NOT NULL COMMENT '项目id',
  `USER_ID` varchar(100) NOT NULL COMMENT '用户id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  UNIQUE INDEX uk_project_user(`project_id`,`USER_ID`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目用户关联';

-- ----------------------------
-- 脚本
-- ----------------------------
DROP TABLE IF EXISTS `t_script`;
CREATE TABLE `t_script` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '脚本名称',
  `type` varchar(40) NOT NULL COMMENT '脚本类型',
  `script_key` varchar(10) DEFAULT NULL COMMENT '脚本key',
  `purpose` varchar(20) DEFAULT NULL COMMENT '用途',
  `username` varchar(40) NOT NULL COMMENT '创建者',
  `url` varchar(1000) DEFAULT NULL COMMENT '路径',
  `filesize` varchar(20) DEFAULT NULL COMMENT '大小',
  `medium_id` bigint unsigned NOT NULL COMMENT '关联介质id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_type(`type`),
  INDEX idx_medium_id(`medium_id`),
  INDEX idx_username(`username`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='脚本'; 


-- ----------------------------
-- 脚本参数
-- ----------------------------
DROP TABLE IF EXISTS `t_script_param`;
CREATE TABLE `t_script_param` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '参数名称',
  `script_param_key` varchar(10) DEFAULT NULL COMMENT '参数key',
  `default_value` varchar(20) DEFAULT NULL COMMENT '默认值',
  `number` int unsigned DEFAULT NULL COMMENT '参数顺序',
  `script_id` bigint unsigned NOT NULL COMMENT '脚本id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_script_id(`script_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='脚本参数'; 


-- ----------------------------
-- 介质
-- ----------------------------
DROP TABLE IF EXISTS `t_medium`;
CREATE TABLE `t_medium` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '介质名称',
  `version` varchar(20) NOT NULL COMMENT '版本号',
  `type` varchar(30) DEFAULT NULL COMMENT '类别',
  `url` varchar(1000) DEFAULT NULL COMMENT '路径',
  `filesize` varchar(20) DEFAULT NULL COMMENT '大小',
  `username` varchar(40) NOT NULL COMMENT '创建者',
  `detail` varchar(500) DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_username(`username`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='介质'; 


-- ----------------------------
-- 安装文档
-- ----------------------------
DROP TABLE IF EXISTS `t_document`;
CREATE TABLE `t_document` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `url` varchar(1000) NOT NULL COMMENT '路径',
  `filesize` varchar(20) NOT NULL COMMENT '大小',
  `detail` varchar(2000) DEFAULT NULL COMMENT '备注',
  `username` varchar(40) NOT NULL COMMENT '创建者',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_username(`username`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安装文档'; 


-- ----------------------------
-- 系统日志
-- ----------------------------
DROP TABLE IF EXISTS `t_system_log`;
create table `t_system_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(40) NOT NULL COMMENT '操作者',
  `type` tinyint unsigned NOT NULL COMMENT '操作类型',
  `opt_object` varchar(20) NOT NULL COMMENT '操作对象',
  `opt_status` tinyint unsigned NOT NULL COMMENT '操作状态',
  `ip` varchar(20) NOT NULL COMMENT '操作者ip',
  `detail` varchar(2000) DEFAULT NULL COMMENT '操作详细描述',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_username(`username`),
  INDEX idx_type(`type`),
  INDEX idx_opt_object(`opt_object`),
  INDEX idx_opt_status(`opt_status`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志'; 

-- ----------------------------
-- 组织机构
-- ----------------------------
DROP TABLE IF EXISTS `oa_department`;
CREATE TABLE `oa_department` (
  `DEPARTMENT_ID` varchar(100) NOT NULL,
  `NAME` varchar(30) DEFAULT NULL COMMENT '名称',
  `NAME_EN` varchar(50) DEFAULT NULL COMMENT '英文',
  `BIANMA` varchar(50) DEFAULT NULL COMMENT '编码',
  `PARENT_ID` varchar(100) DEFAULT NULL COMMENT '上级ID',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  `HEADMAN` varchar(30) DEFAULT NULL COMMENT '负责人',
  `TEL` varchar(50) DEFAULT NULL COMMENT '电话',
  `FUNCTIONS` varchar(255) DEFAULT NULL COMMENT '部门职能',
  `ADDRESS` varchar(255) DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`DEPARTMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- 用户组
-- ----------------------------
DROP TABLE IF EXISTS `t_usergroup`;
create table `t_usergroup` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '用户组名称',
  `ROLE_ID` varchar(40) NOT NULL COMMENT '角色ID',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';

-- ----------------------------
-- 用户
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` varchar(100) NOT NULL COMMENT '用户id',
  `USERNAME` varchar(255) DEFAULT NULL COMMENT '登录名',
  `PASSWORD` varchar(255) DEFAULT NULL COMMENT '密码',
  `NAME` varchar(255) DEFAULT NULL COMMENT '用户名',
  `RIGHTS` varchar(255) DEFAULT NULL COMMENT '',
  `ROLE_ID` varchar(100) DEFAULT NULL COMMENT '关联角色id',
  `LAST_LOGIN` varchar(255) DEFAULT NULL COMMENT '最后登录时间',
  `IP` varchar(100) DEFAULT NULL COMMENT '登录ip',
  `STATUS` varchar(32) DEFAULT NULL COMMENT '状态',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  `SKIN` varchar(100) DEFAULT NULL COMMENT '',
  `EMAIL` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `NUMBER` varchar(100) DEFAULT NULL COMMENT '编号',
  `PHONE` varchar(32) DEFAULT NULL COMMENT '手机号',
  `DEPARTMENT_ID` varchar(100) NOT NULL COMMENT '所属部门id',
  INDEX idx_role_id(`ROLE_ID`),
  INDEX idx_department_id(`DEPARTMENT_ID`),
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- 角色
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` varchar(100) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `ADD_QX` varchar(255) DEFAULT NULL,
  `DEL_QX` varchar(255) DEFAULT NULL,
  `EDIT_QX` varchar(255) DEFAULT NULL,
  `CHA_QX` varchar(255) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- 数据字典
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionaries`;
CREATE TABLE `sys_dictionaries` (
  `DICTIONARIES_ID` varchar(100) NOT NULL,
  `NAME` varchar(30) DEFAULT NULL COMMENT '名称',
  `NAME_EN` varchar(50) DEFAULT NULL COMMENT '英文',
  `BIANMA` varchar(50) DEFAULT NULL COMMENT '编码',
  `ORDER_BY` int(11) NOT NULL COMMENT '排序',
  `PARENT_ID` varchar(100) DEFAULT NULL COMMENT '上级ID',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  `TBSNAME` varchar(100) DEFAULT NULL COMMENT '排查表',
  UNIQUE INDEX uk_bianma(`BIANMA`),
  PRIMARY KEY (`DICTIONARIES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';


insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('028d07972a1e498284548e9bd234f50a','一键安装WebSphere应用','A_key_to_install_websphere','A_key_to_install_websphere','2','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('125f9fe3c8ca4b7e95ff32b4fce2b5d1','一键安装IBMMQ应用','A_key_to_install_IBMMQ','A_key_to_install_IBMMQ','6','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('23fc01915a924c2e9c5d75413e328ae4','分配oracle表空间','allocation_oracle_table_space','allocation_oracle_table_space','4','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('448d299d49494a8cab19b719a8ec70b0','一键安装sqlserver应用','A_key_to_install_sqlserver','A_key_to_install_sqlserver','15','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('468f074a75b54b1daabbf4474a6d3229','oracle表空间管理','oracle_table_space_mgt','oracle_table_space_mgt','12','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('6637c4ff16224740961199e694dbb615','一键编译并安装rabbitmq应用','A_key_to_install_rabbitmq','A_key_to_install_rabbitmq','7','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('6677c9864fef4b07801155d8803e844b','一键安装tomcat二进制应用 ','A_key_to_install_tomcat','A_key_to_install_tomcat','9','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('82926e847dc04260a1f01136954b2089','服务停止脚本','service_stop','service_stop','14','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('854f23ca3f6f497eba6f6010e6373d2c','脚本类型','script_type','script_type','4','0','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('8bd842e07c864a72ad4eaca594acbc09',' 一键安装Apache httpd应用','A_key_to_install_httpd','A_key_to_install_httpd','11','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('a047fb384b404aca8de225459081f092','一键安装Jboss应用','A_key_to_install_jboss','A_key_to_install_jboss','17','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('a4b5f18c9b6645d5b945b658ea31286f','服务启动脚本 ','service_startup','service_startup','13','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('ac296766c42c42aeab77bb57e114c0bb','一键安装redis应用 ','A_key_to_install_redis','A_key_to_install_redis','18','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('adfead646599425c9723dcdac1fdb6a8','一键安装MongoDBServer','A_key_to_install_mongodb','A_key_to_install_mongodb','16','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('ae344b49432343498359b9ab062fe6ed','JBoss自动安装脚本','jboss_auto_script','jboss_auto_script','1','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('d8530943d9d341f48c9a4f8238a0a7fe','添加oracle用户','add_oracle_user','add_oracle_user','3','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('dc208abc329149968edeb048178ee49d','一键安装JDK二进制应用','A_key_to_install_jdk','A_key_to_install_jdk','8','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('e9d3b42a4a154211b03b742c333c127d','一键安装DB2应用','A_key_to_install_db2','A_key_to_install_db2','5','854f23ca3f6f497eba6f6010e6373d2c','','');
insert into `sys_dictionaries` (`DICTIONARIES_ID`, `NAME`, `NAME_EN`, `BIANMA`, `ORDER_BY`, `PARENT_ID`, `BZ`, `TBSNAME`) values('ebaac0b1ecb744aa8768213862f7272c','一键安装mysql server5.6 ','A_key_to_install_msyql_server56','A_key_to_install_msyql_server56','10','854f23ca3f6f497eba6f6010e6373d2c','','');



