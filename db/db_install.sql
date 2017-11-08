
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
-- 数据中心
-- ----------------------------
DROP TABLE IF EXISTS `t_datacenter`;
CREATE TABLE `t_datacenter` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `areacode` varchar(20) DEFAULT NULL COMMENT '地域',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_areacode(`areacode`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据中心'; 

-- ----------------------------
-- 资源池
-- ----------------------------
DROP TABLE IF EXISTS `t_resourcepool`;
CREATE TABLE `t_resourcepool` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `type` tinyint unsigned NOT NULL COMMENT '类型',
  `ip` varchar(20) NOT NULL COMMENT '资源池ip或域名',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_type(`type`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源池'; 

-- ----------------------------
-- 集群
-- ----------------------------
DROP TABLE IF EXISTS `t_cluster`;
CREATE TABLE `t_cluster` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `resourcepool_id` bigint unsigned DEFAULT NULL COMMENT '资源池id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_resourcepool_id(`resourcepool_id`),
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
-- 资源池与网络关联
-- ----------------------------
DROP TABLE IF EXISTS `t_resourcepool_network_map`;
CREATE TABLE `t_resourcepool_network_map` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `resourcepool_id` bigint unsigned NOT NULL COMMENT '资源池id',
  `network_id` bigint unsigned NOT NULL COMMENT '网络id',
  `ippool` varchar(40) DEFAULT NULL COMMENT 'ip池',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_resourcepool_id(`resourcepool_id`),
  INDEX idx_network_id(`network_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源池与网络关联'; 

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
  `user_id` bigint unsigned NOT NULL COMMENT '申请者id',
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
  INDEX idx_user_id(`user_id`),
  INDEX idx_status(`status`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单'; 


-- ----------------------------
-- 订单
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint unsigned NOT NULL,
  `user_id` bigint unsigned NOT NULL COMMENT '申请者id',
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
  INDEX idx_user_id(`user_id`),
  INDEX idx_status(`status`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单'; 


-- ----------------------------
-- 项目
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '项目名称',
  `shortname` varchar(20) DEFAULT NULL COMMENT '项目简称',
  `level` varchar(5) DEFAULT NULL COMMENT '项目等级',
  `parent_id` bigint unsigned NOT NULL COMMENT '父项目id',
  `powergroup_id` bigint unsigned NOT NULL COMMENT '项目所归属的组织',
  `poweruser_id` bigint unsigned NOT NULL COMMENT '负责人',
  `auditrole_id` bigint unsigned NOT NULL COMMENT '审核角色id',
  `detail` varchar(500) DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_level(`level`),
  INDEX idx_parent_idd(`parent_id`),
  INDEX idx_powergroup_id(`powergroup_id`),
  INDEX idx_poweruser_id(`poweruser_id`),
  INDEX idx_auditrole_id(`auditrole_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目'; 


-- ----------------------------
-- 脚本
-- ----------------------------
DROP TABLE IF EXISTS `t_script`;
CREATE TABLE `t_script` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '脚本名称',
  `type` tinyint unsigned NOT NULL COMMENT '脚本类型',
  `script_key` varchar(10) DEFAULT NULL COMMENT '脚本key',
  `purpose` varchar(20) DEFAULT NULL COMMENT '用途',
  `user_id` bigint unsigned NOT NULL COMMENT '创建者id',
  `url` varchar(1000) DEFAULT NULL COMMENT '路径',
  `medium_id` bigint unsigned NOT NULL COMMENT '关联介质id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_type(`type`),
  INDEX idx_medium_id(`medium_id`),
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
  `purpose` varchar(20) DEFAULT NULL COMMENT '用途',
  `url` varchar(1000) DEFAULT NULL COMMENT '路径',
  `user_id` bigint unsigned NOT NULL COMMENT '创建者id',
  `detail` varchar(500) DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_user_id(`user_id`),
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
  `user_id` varchar(40) NOT NULL COMMENT '创建者id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_user_id(`user_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='安装文档'; 


-- ----------------------------
-- 系统日志
-- ----------------------------
DROP TABLE IF EXISTS `t_system_log`;
create table `t_system_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL COMMENT '操作者id',
  `type` tinyint unsigned NOT NULL COMMENT '操作类型',
  `opt_object` varchar(20) NOT NULL COMMENT '操作对象',
  `opt_status` tinyint unsigned NOT NULL COMMENT '操作状态',
  `ip` varchar(20) NOT NULL COMMENT '操作者ip',
  `detail` varchar(2000) DEFAULT NULL COMMENT '操作详细描述',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_user_id(`user_id`),
  INDEX idx_type(`type`),
  INDEX idx_opt_object(`opt_object`),
  INDEX idx_opt_status(`opt_status`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志'; 


-- ----------------------------
-- 用户组
-- ----------------------------
DROP TABLE IF EXISTS `t_usergroup`;
create table `t_usergroup` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '用户组名称',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组';


-- ----------------------------
-- 用户组与角色关联
-- ----------------------------
DROP TABLE IF EXISTS `t_usergroup_role_map`;
create table `t_usergroup_role_map` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `usergroup_id` bigint unsigned NOT NULL COMMENT '用户组id',
  `role_id` bigint unsigned NOT NULL COMMENT '角色id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  UNIQUE INDEX uk_usergroup_role(`usergroup_id`,`role_id`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户组与角色关联';



