DROP table if EXISTS cmp_workorder;
CREATE TABLE `cmp_workorder` (
  `appNo` varchar(20) NOT NULL COMMENT '申请编号：A+YYYYMMDD+00001',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `orderNo` varchar(20) NOT NULL COMMENT '清单编号：O+YYYYMMDD+00001',
  `appType` varchar(20) DEFAULT NULL COMMENT '申请类型：1-资源申请；2-运维服务申请',
  `status` varchar(10) DEFAULT NULL COMMENT '状态：0-待提交；1-审批中；2-审批通过；3-审批不通过',
  `procInstId` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  `applyUserId` varchar(20) NOT NULL COMMENT '申请者',
  `areaCode` varchar(20) DEFAULT NULL COMMENT '地域代码',
  `platType` varchar(32) DEFAULT NULL COMMENT '平台类型',
  `deployType` varchar(20) DEFAULT NULL COMMENT '部署类型',
  `envCode` varchar(20) DEFAULT NULL COMMENT '环境代码',
  `resType` varchar(20) DEFAULT NULL COMMENT '资源类型',
  `virName` varchar(60) DEFAULT NULL COMMENT '虚拟机名称',
  `virIp` varchar(20) DEFAULT NULL COMMENT '虚拟机IP',
  `cpu` varchar(10) DEFAULT NULL COMMENT 'CPU',
  `memory` varchar(10) DEFAULT NULL COMMENT '内存',
  `diskType` varchar(3000) DEFAULT NULL COMMENT '磁盘类型，多个用英文逗号分隔',
  `diskSize` varchar(3000) DEFAULT NULL COMMENT '磁盘大小，多个用英文逗号分隔',
  `diskEncrypt` varchar(3000) DEFAULT NULL COMMENT '磁盘加密，多个用英文逗号分隔',
  `softCode` varchar(3000) DEFAULT NULL COMMENT '软件代码，多个用英文逗号分隔',
  `softParam` varchar(3000) DEFAULT NULL COMMENT '软件参数，多个用英文逗号分隔',
  `projectCode` varchar(32) DEFAULT NULL COMMENT '项目代码',
  `osType` varchar(20) DEFAULT NULL COMMENT '操作系统类型',
  `osBitNum` varchar(10) DEFAULT NULL COMMENT '操作系统位数',
  `imgCode` varchar(20) DEFAULT NULL COMMENT '镜像代码',
  `imgUserName` varchar(60) DEFAULT NULL COMMENT '镜像用户名',
  `imgUserPass` varchar(60) DEFAULT NULL COMMENT '镜像用户密码',
  `imgPath` varchar(300) DEFAULT NULL COMMENT '镜像路径',
  `expireDate` varchar(19) DEFAULT NULL COMMENT '到期时间',
  `virNum` varchar(10) DEFAULT NULL COMMENT '虚拟机数量',
   `uploadFileName` varchar(200) DEFAULT NULL COMMENT '上传文件名',
  `executeStatus` varchar(10) DEFAULT NULL COMMENT '工单实施状态  0 未实施   1开始实施   2实施完毕   3 实施作废',
  `virtualMachineId` bigint unsigned DEFAULT NULL COMMENT '虚拟机ID',
  PRIMARY KEY (`appNo`),
  KEY `indx_cmp_workorder_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单表';