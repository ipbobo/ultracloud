DROP table if EXISTS cmp_order;
CREATE TABLE `cmp_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `status` varchar(10) DEFAULT NULL COMMENT '状态：0-待提交；1-已提交；T-套餐',
  `applyUserId` varchar(20) NOT NULL COMMENT '申请者',
  `areaCode` varchar(20) DEFAULT NULL COMMENT '地域代码',
  `platType` varchar(20) DEFAULT NULL COMMENT '平台类型',
  `deployType` varchar(20) DEFAULT NULL COMMENT '部署类型',
  `envCode` varchar(20) DEFAULT NULL COMMENT '环境代码',
  `resType` varchar(20) DEFAULT NULL COMMENT '资源类型',
  `virName` varchar(60) DEFAULT NULL COMMENT '虚拟机名称',
  `virIp` varchar(20) DEFAULT NULL COMMENT '虚拟机IP',
  `cpu` varchar(10) DEFAULT NULL COMMENT 'CPU',
  `memory` varchar(10) DEFAULT NULL COMMENT '内存',
  `diskType` varchar(300) DEFAULT NULL COMMENT '磁盘类型，多个用英文逗号分隔',
  `diskSize` varchar(300) DEFAULT NULL COMMENT '磁盘大小，多个用英文逗号分隔',
  `diskEncrypt` varchar(300) DEFAULT NULL COMMENT '磁盘加密，多个用英文逗号分隔',
  `softName` varchar(300) DEFAULT NULL COMMENT '软件名称，多个用英文逗号分隔',
  `softVer` varchar(300) DEFAULT NULL COMMENT '软件版本，多个用英文逗号分隔',
  `softParam` varchar(300) DEFAULT NULL COMMENT '软件参数，多个用英文逗号分隔',
  `projectCode` varchar(32) DEFAULT NULL COMMENT '项目代码',
  `osType` varchar(20) DEFAULT NULL COMMENT '操作系统类型',
  `osBitNum` varchar(10) DEFAULT NULL COMMENT '操作系统位数',
  `imgCode` varchar(20) DEFAULT NULL COMMENT '镜像代码',
  `imgUserName` varchar(60) DEFAULT NULL COMMENT '镜像用户名',
  `imgUserPass` varchar(60) DEFAULT NULL COMMENT '镜像用户密码',
  `imgPath` varchar(300) DEFAULT NULL COMMENT '镜像路径',
  `imgExpireDate` varchar(19) DEFAULT NULL COMMENT '镜像到期时间',
  `expireDate` varchar(19) DEFAULT NULL COMMENT '到期时间',
  `virNum` varchar(10) DEFAULT NULL COMMENT '虚拟机数量',
  `pckgName` varchar(60) DEFAULT NULL COMMENT '套餐名称',
  PRIMARY KEY (`id`),
  KEY `indx_cmp_order_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='清单表';


DROP TABLE IF EXISTS `cmp_op_serve`;
CREATE TABLE `cmp_op_serve` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `serviceType` varchar(20) NOT NULL COMMENT '务服类型',
  `operType` varchar(20) NOT NULL COMMENT '作操类型',
  `vm` varchar(20) DEFAULT NULL COMMENT '拟机虚',
  `vmMsg` varchar(500) DEFAULT NULL COMMENT '请申虚拟机操作说明',
  `middleware` varchar(20) DEFAULT NULL COMMENT '间件中',
  `middlewareMsg` varchar(500) DEFAULT NULL COMMENT '请申中间件操作说明',
  `appmsg` varchar(500) DEFAULT NULL COMMENT '维运申请说明',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '建创时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;