
-- ----------------------------
-- 环境
-- ----------------------------
DROP TABLE IF EXISTS `t_environment`;
CREATE TABLE `t_environment` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `disknum` int NOT NULL COMMENT '挂载云磁盘数量',
  `diskmaximum` int NOT NULL COMMENT '每块云磁盘最大值',
  `softnum` int NOT NULL COMMENT '安装软件数量',
  `datacenter_id` bigint unsigned DEFAULT NULL COMMENT '数据中心id',
  `duetopolicy` varchar(20) NOT NULL COMMENT '资源到期策略',
  `is_initsnapshoot` varchar(20) NOT NULL COMMENT '是否要初始快照   1 - 是; 0 - 否',
  `USERNAME` varchar(20) NOT NULL COMMENT '创建人',
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
  `id` varchar(32) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT '名称',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` varchar(20) NOT NULL COMMENT '同步版本号',
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云平台'; 

-- ----------------------------
-- 云平台同步表
-- ----------------------------
DROP TABLE IF EXISTS `t_cloudplatform_sync`;
CREATE TABLE `t_cloudplatform_sync` (
  `id` varchar(32) NOT NULL,
  `name` varchar(30) NOT NULL COMMENT '名称',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `username` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` varchar(20) NOT NULL COMMENT '同步版本号',
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='云平台同步表'; 

-- ----------------------------
-- 数据中心
-- ----------------------------
DROP TABLE IF EXISTS `t_datacenter`;
CREATE TABLE `t_datacenter` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
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
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  `datacenter_id` varchar(32) DEFAULT NULL COMMENT '数据中心id',
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
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) DEFAULT COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  `datacenter_id` varchar(32) DEFAULT NULL COMMENT '数据中心id',
  `cluster_id` varchar(32) DEFAULT NULL COMMENT '集群id',
  `status` tinyint unsigned DEFAULT NULL COMMENT '状态',
  `ip` varchar(20) DEFAULT NULL COMMENT '宿主机ip',
  `port` INT UNSIGNED DEFAULT NULL COMMENT '端口',
  `cpu` double DEFAULT NULL COMMENT 'cpu',
  `cpu_used` double DEFAULT NULL COMMENT '已使用cpu',
  `memory` double DEFAULT NULL COMMENT '内存',
  `memory_used` double DEFAULT NULL COMMENT '已使用内存',
  `localdisk` float DEFAULT NULL COMMENT '本地磁盘',
  `localdisk_used` float DEFAULT NULL COMMENT '已使用本地磁盘',
  `devicenum` varchar(30) DEFAULT NULL COMMENT '设备号',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `account` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password_ssh` varchar(20) DEFAULT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_type(`type`),
  INDEX idx_status(`status`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='宿主机'; 

-- ----------------------------
-- 宿主机同步表
-- ----------------------------
DROP TABLE IF EXISTS `t_hostmachine_sync`;
CREATE TABLE `t_hostmachine_sync` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) DEFAULT COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  `datacenter_id` varchar(32) DEFAULT NULL COMMENT '数据中心id',
  `cluster_id` varchar(32) DEFAULT NULL COMMENT '集群id',
  `status` tinyint unsigned DEFAULT NULL COMMENT '状态',
  `ip` varchar(20) DEFAULT NULL COMMENT '宿主机ip',
  `port` INT UNSIGNED DEFAULT NULL COMMENT '端口',
  `cpu` double DEFAULT NULL COMMENT 'cpu',
  `cpu_used` double DEFAULT NULL COMMENT '已使用cpu',
  `memory` double DEFAULT NULL COMMENT '内存',
  `memory_used` double DEFAULT NULL COMMENT '已使用内存',
  `localdisk` float DEFAULT NULL COMMENT '本地磁盘',
  `localdisk_used` float DEFAULT NULL COMMENT '已使用本地磁盘',
  `devicenum` varchar(30) DEFAULT NULL COMMENT '设备号',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `account` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password_ssh` varchar(20) DEFAULT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` varchar(20) NOT NULL COMMENT '同步版本号',
  `isSelected` varchar(20) DEFAULT NULL COMMENT '选中并激活',
  INDEX idx_type(`type`),
  INDEX idx_status(`status`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='宿主机同步表'; 

-- ----------------------------
-- 存储
-- ----------------------------
DROP TABLE IF EXISTS `t_storage`;
CREATE TABLE `t_storage` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  `datacenter_id` varchar(32) DEFAULT NULL COMMENT '数据中心id',
  `allspace` float DEFAULT NULL COMMENT '所有空间',
  `freespace` float DEFAULT NULL COMMENT '可用空间',
  `threshold` tinyint unsigned DEFAULT NULL COMMENT '阈值',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_type(`type`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储'; 

-- ----------------------------
-- 存储同步表
-- ----------------------------
DROP TABLE IF EXISTS `t_storage_sync`;
CREATE TABLE `t_storage_sync` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  `datacenter_id` varchar(32) DEFAULT NULL COMMENT '数据中心id',
  `allspace` float DEFAULT NULL COMMENT '所有空间',
  `freespace` float DEFAULT NULL COMMENT '可用空间',
  `threshold` tinyint unsigned DEFAULT NULL COMMENT '阈值',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` varchar(20) NOT NULL COMMENT '同步版本号',
  `isSelected` varchar(20) DEFAULT NULL COMMENT '选中并激活',
  INDEX idx_type(`type`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='存储同步表'; 

-- ----------------------------
-- 数据中心网络
-- ----------------------------
DROP TABLE IF EXISTS `t_datacenter_network`;
CREATE TABLE `t_datacenter_network` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  `datacenter_id` varchar(32) DEFAULT NULL COMMENT '数据中心id',
  `ippool` varchar(40) DEFAULT NULL COMMENT 'ip池',
  `mask` varchar(20) DEFAULT NULL COMMENT '掩码',
  `gateway` varchar(20) DEFAULT NULL COMMENT '网关',
  `dns1` varchar(20) DEFAULT NULL COMMENT 'DNS1',
  `dns2` varchar(20) DEFAULT NULL COMMENT 'DNS2',
  `totalip` int unsigned DEFAULT NULL COMMENT 'ip总数',
  `freeip` int unsigned DEFAULT NULL COMMENT 'ip剩余量',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_datacenter_id(`datacenter_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据中心网络'; 

-- ----------------------------
-- 数据中心网络同步表
-- ----------------------------
DROP TABLE IF EXISTS `t_datacenter_network_sync`;
CREATE TABLE `t_datacenter_network_sync` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '名称',
  `uuid` varchar(40) NOT NULL COMMENT 'uuid',
  `type` varchar(20) NOT NULL COMMENT '类型',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  `datacenter_id` varchar(32) DEFAULT NULL COMMENT '数据中心id',
  `ippool` varchar(40) DEFAULT NULL COMMENT 'ip池',
  `mask` varchar(20) DEFAULT NULL COMMENT '掩码',
  `gateway` varchar(20) DEFAULT NULL COMMENT '网关',
  `dns1` varchar(20) DEFAULT NULL COMMENT 'DNS1',
  `dns2` varchar(20) DEFAULT NULL COMMENT 'DNS2',
  `totalip` int unsigned DEFAULT NULL COMMENT 'ip总数',
  `freeip` int unsigned DEFAULT NULL COMMENT 'ip剩余量',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` varchar(20) NOT NULL COMMENT '同步版本号',
  `isSelected` varchar(20) DEFAULT NULL COMMENT '选中并激活',
  INDEX idx_datacenter_id(`datacenter_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据中心网络同步表'; 

-- ----------------------------
-- 虚拟机
-- ----------------------------
DROP TABLE IF EXISTS `t_virtualmachine`;
CREATE TABLE `t_virtualmachine` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(20) NOT NULL COMMENT '虚拟机用户',
  `appNo` varchar(20) NOT NULL COMMENT '工单编号',
  `project_id` varchar(32) NOT NULL COMMENT '项目名称',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `envCode` varchar(20) DEFAULT NULL COMMENT '环境代码',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '数据盘',
  `status` tinyint unsigned NOT NULL COMMENT '状态',
  `hostmachine_id` bigint unsigned NOT NULL COMMENT '宿主机id',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台',
  `os` varchar(20) DEFAULT NULL COMMENT '操作系统',
  `os_status` varchar(20) DEFAULT NULL COMMENT '操作系统安装状态',
  `soft` varchar(20) DEFAULT NULL COMMENT '软件',
  `soft_status` varchar(20) DEFAULT NULL COMMENT '软件安装状态',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `uuid` varchar(40) DEFAULT NULL COMMENT 'uuid',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  INDEX idx_status(`status`),
  INDEX idx_hostmachine_id(`hostmachine_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟机'; 

-- ----------------------------
-- 虚拟机同步表
-- ----------------------------
DROP TABLE IF EXISTS `t_virtualmachine_sync`;
CREATE TABLE `t_virtualmachine_sync` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user` varchar(20) NOT NULL COMMENT '虚拟机用户',
  `appNo` varchar(20) NOT NULL COMMENT '工单编号',
  `project_id` varchar(32) NOT NULL COMMENT '项目名称',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `envCode` varchar(20) DEFAULT NULL COMMENT '环境代码',
  `ip` varchar(20) DEFAULT NULL COMMENT '虚拟机ip',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '数据盘',
  `status` tinyint unsigned NOT NULL COMMENT '状态',
  `hostmachine_id` bigint unsigned NOT NULL COMMENT '宿主机id',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台',
  `os` varchar(20) DEFAULT NULL COMMENT '操作系统',
  `os_status` varchar(20) DEFAULT NULL COMMENT '操作系统安装状态',
  `soft` varchar(20) DEFAULT NULL COMMENT '软件',
  `soft_status` varchar(20) DEFAULT NULL COMMENT '软件安装状态',
  `duedate` datetime DEFAULT NULL COMMENT '到期时间',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(20) NOT NULL COMMENT '密码',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `uuid` varchar(40) DEFAULT NULL COMMENT 'uuid',
  `cpf_id` varchar(32) DEFAULT NULL COMMENT '云平台id',
  INDEX idx_status(`status`),
  INDEX idx_hostmachine_id(`hostmachine_id`),
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='虚拟机同步表'; 

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
  `bitrate` int unsigned DEFAULT NULL COMMENT '位数',
  `USERNAME` varchar(35) NOT NULL COMMENT '创建者id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_USERNAME(`USERNAME`),
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
  `cloudplatform_id` bigint unsigned NOT NULL COMMENT '云平台id',
  `datacenter_id` bigint unsigned NOT NULL COMMENT '数据中心id',
  `ostype` varchar(20) DEFAULT NULL COMMENT '操作系统类型',
  `osname` varchar(20) DEFAULT NULL COMMENT '操作系统名称',
  `bitrate` tinyint unsigned DEFAULT NULL COMMENT '位数',
  `platform` varchar(20) DEFAULT NULL COMMENT '平台',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_datacenter_id(`datacenter_id`),
  INDEX idx_cloudplatform_id(`cloudplatform_id`),
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
  `cpu_quota` int(11) DEFAULT NULL COMMENT 'cpu配额',
  `memory_quota` int(11) DEFAULT NULL COMMENT '内存配额',
  `disk_quota` int(11) DEFAULT NULL COMMENT '磁盘配额',
  `cpu_used` int(11) DEFAULT NULL COMMENT '已使用cpu',
  `memory_used` int(11) DEFAULT NULL COMMENT '已使用内存',
  `disk_used` int(11) DEFAULT NULL COMMENT '已使用磁盘',
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
  `param_key` varchar(10) DEFAULT NULL COMMENT '参数key',
  `value` varchar(20) DEFAULT NULL COMMENT '默认值',
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
  `type` tinyint unsigned NOT NULL COMMENT '操作类型：1-新增；2-修改；3-删除',
  `opt_object` varchar(20) NOT NULL COMMENT '操作对象',
  `opt_status` varchar(5) NOT NULL COMMENT '操作状态：0-成功；-1-失败',
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
-- 系统日志
-- ----------------------------
DROP TABLE IF EXISTS `cmp_sys_config`;
create table `cmp_sys_config` (
  `sys_name` varchar(100) NOT NULL COMMENT '系统名称',
  `logo` varchar(200) DEFAULT NULL COMMENT '系统图标',
  `company_name` varchar(100)DEFAULT NULL COMMENT '公司名称',
  `company_shortName` varchar(100) DEFAULT NULL COMMENT '公司简称',
  `tel` varchar(30) DEFAULT NULL COMMENT '技术支持热线',
  `product_consultion` varchar(100) DEFAULT NULL COMMENT '产品咨询',
  `website` varchar(100) DEFAULT NULL COMMENT '公司站点',
  `copr` varchar(100) DEFAULT NULL COMMENT '版权信息',
  PRIMARY KEY (`sys_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

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
  `cpu_quota` int(11) DEFAULT NULL COMMENT 'cpu配额',
  `memory_quota` int(11) DEFAULT NULL COMMENT '内存配额',
  `disk_quota` int(11) DEFAULT NULL COMMENT '磁盘配额',
  `cpu_used` int(11) DEFAULT NULL COMMENT '已使用cpu',
  `memory_used` int(11) DEFAULT NULL COMMENT '已使用内存',
  `disk_used` int(11) DEFAULT NULL COMMENT '已使用磁盘',
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
-- 用户组与用户关联
-- ----------------------------
DROP TABLE IF EXISTS `t_usergroup_user_map`;
CREATE TABLE `t_usergroup_user_map` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `usergroup_id` bigint(20) unsigned NOT NULL COMMENT '用户组id',
  `USER_ID` varchar(100) NOT NULL COMMENT '用户id',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_usergroup_role` (`usergroup_id`,`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户组与用户关联';

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
  `TEL` varchar(20) DEFAULT NULL COMMENT '座机',
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
  `RIGHTS` varchar(2000) DEFAULT NULL,
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



-- ----------------------------
-- 计算方案
-- ----------------------------
DROP TABLE IF EXISTS `t_numprocedure`;
CREATE TABLE `t_numprocedure` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '计算方案名称',
  `showname` varchar(1000) NOT NULL COMMENT '展示名称',
  `cpu` varchar(20) NOT NULL COMMENT 'cpu',
  `memory` varchar(2000) DEFAULT NULL COMMENT '内存',
  `isRecommand` varchar(40) NOT NULL COMMENT '是否为推荐配置',
  `USERNAME` varchar(20) NOT NULL COMMENT '创建人',
  `ordernum` int(11) NOT NULL COMMENT '排序值',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  INDEX idx_username(`USERNAME`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计算方案'; 

-- ----------------------------
-- 自动安装规则
-- ----------------------------
DROP TABLE IF EXISTS `t_autoinstall_rule`;
CREATE TABLE `t_autoinstall_rule` (
  `id` bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cluster_id` varchar(32)  NOT NULL COMMENT '集群id',
  `type` varchar(20) NOT NULL COMMENT '集群类型',
  `num_rule` varchar(40) DEFAULT NULL COMMENT '计算规则',
  `storage_rule` varchar(40) DEFAULT NULL COMMENT '存储规则',
  `ip_rule` varchar(40) DEFAULT NULL COMMENT 'ip规则',
  `openstack_rule` varchar(40) DEFAULT NULL COMMENT 'openstack规则',
  `USERNAME` varchar(20) NOT NULL COMMENT '创建者',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自动安装规则'; 

-- ----------------------------
-- 产品
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '产品名称',
  `type` varchar(20) NOT NULL COMMENT '产品类型',
  `unit` int(11)  NOT NULL COMMENT '计价单位',
  `price_old` int(11)  NOT NULL COMMENT '旧价格',
  `price_cur` int(11)  NOT NULL COMMENT '当前价格',
  `price_new` int(11)  NOT NULL COMMENT '新价格',
  `isImediate` varchar(10) NOT NULL COMMENT '是否立即生效',
  `clockTime` varchar(20) DEFAULT NULL COMMENT '定时时间',
  `USERNAME` varchar(20) NOT NULL COMMENT '创建者',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品'; 

DROP TABLE IF EXISTS `t_deployed_soft`;
CREATE TABLE `t_deployed_soft` (
`id`  int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
`virtualmachineId`  varchar(32) DEFAULT NULL COMMENT '虚拟机ID',
`virtualmachineName`  varchar(50) DEFAULT NULL COMMENT '虚拟机名称' ,
`softName`  varchar(50) DEFAULT NULL COMMENT '软件名称' ,
`softParams`  varchar(200) DEFAULT NULL COMMENT '软件参数' ,
`softType`  varchar(50) DEFAULT NULL COMMENT '软件类型' ,
`softVersion`  varchar(30) DEFAULT NULL COMMENT '软件版本' ,
`status`  varchar(10) DEFAULT NULL COMMENT '部署状态' ,
`remark`  varchar(50) DEFAULT NULL COMMENT '其他' ,
PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='软件部署表';

-- ----------------------------
-- 计费统计报表(按天)
-- ----------------------------
DROP TABLE IF EXISTS `bi_bill_day`;
CREATE TABLE `bi_bill_day` (
  `id` bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DEPARTMENT_ID` varchar(32) DEFAULT NULL COMMENT '部门id',
  `DEPARTMENT_NAME` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `project_id`varchar(32) DEFAULT NULL COMMENT '项目id',
  `project_name`varchar(32) DEFAULT NULL COMMENT '项目名称',
  `vm_id` bigint unsigned  DEFAULT NULL  COMMENT '虚拟机id',
  `vm_name`varchar(50) DEFAULT NULL COMMENT '虚拟机名称',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '磁盘',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `user` varchar(32) DEFAULT NULL COMMENT '虚拟机用户',
  `date` varchar(20) DEFAULT NULL COMMENT '日期',
  `account` int unsigned DEFAULT NULL COMMENT '费用',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY `uk_vmid_date` (`vm_id`,`date`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计费统计报表(按天)'; 


-- ----------------------------
-- 计费统计报表(按月)
-- ----------------------------
DROP TABLE IF EXISTS `bi_bill_month`;
CREATE TABLE `bi_bill_month` (
  `id` bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DEPARTMENT_ID` varchar(32) DEFAULT NULL COMMENT '部门id',
  `DEPARTMENT_NAME` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `project_id`varchar(32) DEFAULT NULL COMMENT '项目id',
  `project_name`varchar(32) DEFAULT NULL COMMENT '项目名称',
  `vm_id` bigint unsigned  DEFAULT NULL  COMMENT '虚拟机id',
  `vm_name`varchar(50) DEFAULT NULL COMMENT '虚拟机名称',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '磁盘',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `user` varchar(32) DEFAULT NULL COMMENT '虚拟机用户',
  `month` varchar(20) DEFAULT NULL COMMENT '月份',
  `account` int unsigned DEFAULT NULL COMMENT '费用',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY `uk_vmid_month` (`vm_id`,`month`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计费统计报表(按月)'; 


-- ----------------------------
-- 软件台账报表
-- ----------------------------
DROP TABLE IF EXISTS `bi_software_bill`;
CREATE TABLE `bi_software_bill` (
  `id` bigint unsigned  NOT NULL AUTO_INCREMENT COMMENT '主键',
  `DEPARTMENT_ID` varchar(32) DEFAULT NULL COMMENT '部门id',
  `DEPARTMENT_NAME` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `project_id`varchar(32) DEFAULT NULL COMMENT '项目id',
  `project_name`varchar(32) DEFAULT NULL COMMENT '项目名称',
  `vm_id` bigint unsigned  DEFAULT NULL  COMMENT '虚拟机id',
  `vm_name`varchar(50) DEFAULT NULL COMMENT '虚拟机名称',
  `cpu` tinyint unsigned DEFAULT NULL COMMENT 'cpu',
  `memory` int unsigned DEFAULT NULL COMMENT '内存',
  `datadisk` int unsigned DEFAULT NULL COMMENT '磁盘',
  `type` varchar(20) DEFAULT NULL COMMENT '类型',
  `user` varchar(32) DEFAULT NULL COMMENT '虚拟机用户',
  `date` varchar(20) DEFAULT NULL COMMENT '日期',
  `soft_name` varchar(20) DEFAULT NULL COMMENT '软件名称',
  `soft_version` varchar(20) DEFAULT NULL COMMENT '软件版本号',
  `soft_type` varchar(30) DEFAULT NULL COMMENT '软件类型',
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  UNIQUE KEY `uk_vmid_date` (`vm_id`,`soft_type`,`date`),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='软件台账报表'; 

-- ----------------------------
-- 定时任务表
-- ----------------------------
DROP TABLE IF EXISTS `t_timetask`;
CREATE TABLE `t_timetask` (
  `TIMINGBACKUP_ID` varchar(32) NOT NULL,
  `JOBNAME` varchar(50) DEFAULT NULL COMMENT '任务名称',
  `CREATE_TIME` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `CLASSNAME` varchar(50) DEFAULT NULL COMMENT '任务执行类',
  `STATUS` int(1) NOT NULL COMMENT '类型',
  `FHTIME` varchar(30) DEFAULT NULL COMMENT '时间规则',
  `TIMEEXPLAIN` varchar(100) DEFAULT NULL COMMENT '规则说明',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`TIMINGBACKUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务表'; 

-- ----------------------------
-- 系统公告
-- ----------------------------
DROP TABLE IF EXISTS `t_systemnotice`;
CREATE TABLE `t_systemnotice` (
  `FHSMS_ID` varchar(100) NOT NULL,
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `CONTENT` varchar(1000) DEFAULT NULL COMMENT '内容',
  `TYPE` varchar(5) DEFAULT NULL COMMENT '类型',
  `TO_USERNAME` varchar(255) DEFAULT NULL COMMENT '收信人',
  `FROM_USERNAME` varchar(255) DEFAULT NULL COMMENT '发信人',
  `SEND_TIME` varchar(100) DEFAULT NULL COMMENT '发信时间',
  `STATUS` varchar(5) DEFAULT NULL COMMENT '状态',
  `SANME_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`FHSMS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统公告'; 

-- ---------------------------------------------------------------------------------------
-- 创建虚拟机费用查询视图 start...
-- ----------------------------------------------------------------------------------------
DELIMITER $$

USE `cmpdb`$$

DROP VIEW IF EXISTS `v_bi_virtual_bill`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `v_bi_virtual_bill` AS 
SELECT
  `b`.`DEPARTMENT_ID` AS `DEPARTMENT_ID`,
  `b`.`NAME`          AS `DEPARTMENT_NAME`,
  `c`.`id`            AS `project_id`,
  `c`.`name`          AS `project_name`,
  `a`.`id`            AS `vm_id`,
  `a`.`name`          AS `vm_name`,
  `a`.`cpu`           AS `cpu`,
  `a`.`memory`        AS `memory`,
  `a`.`datadisk`      AS `datadisk`,
  `a`.`type`          AS `type`,
  `a`.`user`          AS `user`,
  `a`.`soft`          AS `soft`,
  `a`.`status`        AS `status`,
  `a`.`gmt_create`    AS `gmt_create`,
  `a`.`duedate`       AS `duedate`
FROM ((`t_virtualmachine` `a`
    JOIN `oa_department` `b`)
   JOIN `t_project` `c`)
WHERE ((`a`.`project_id` = `c`.`id`)
       AND (`c`.`DEPARTMENT_ID` = `b`.`DEPARTMENT_ID`))$$

DELIMITER ;
-- ---------------------------------------------------------------------------------------
-- 创建虚拟机费用查询视图 end.
-- ----------------------------------------------------------------------------------------


-- ---------------------------------------------------------------------------------------
-- 创建软件台帐查询视图 start...
-- ----------------------------------------------------------------------------------------
DELIMITER $$

USE `cmpdb`$$

DROP VIEW IF EXISTS `v_bi_soft_bill`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `v_bi_soft_bill` AS 
SELECT
  `b`.`DEPARTMENT_ID` AS `DEPARTMENT_ID`,
  `b`.`NAME`          AS `DEPARTMENT_NAME`,
  `c`.`id`            AS `project_id`,
  `c`.`name`          AS `project_name`,
  `a`.`id`            AS `vm_id`,
  `a`.`name`          AS `vm_name`,
  `a`.`cpu`           AS `cpu`,
  `a`.`memory`        AS `memory`,
  `a`.`datadisk`      AS `datadisk`,
  `a`.`type`          AS `type`,
  `a`.`user`          AS `user`,
  `d`.`softType`      AS `soft_type`,
  `d`.`softName`      AS `soft_name`,
  `d`.`softVersion`   AS `soft_version`
FROM (((`t_virtualmachine` `a`
     JOIN `oa_department` `b`)
    JOIN `t_project` `c`)
   JOIN `t_deployed_soft` `d`)
WHERE ((`a`.`project_id` = `c`.`id`)
       AND (`c`.`DEPARTMENT_ID` = `b`.`DEPARTMENT_ID`)
       AND (`a`.`id` = `d`.`virtualmachineId`))$$

DELIMITER ;
-- ---------------------------------------------------------------------------------------
-- 创建软件台帐查询视图 end.
-- ----------------------------------------------------------------------------------------
