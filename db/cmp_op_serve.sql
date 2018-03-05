DROP TABLE IF EXISTS `cmp_op_serve`;
CREATE TABLE `cmp_op_serve` (
  `id` varchar(20) NOT NULL COMMENT 'ID',
  `serviceType` varchar(20) NOT NULL COMMENT '务服类型',
  `operType` varchar(20) NOT NULL COMMENT '作操类型',
  `vm` varchar(20) DEFAULT NULL COMMENT '拟机虚',
  `vmMsg` varchar(500) DEFAULT NULL COMMENT '请申虚拟机操作说明',
  `middleware` varchar(20) DEFAULT NULL COMMENT '间件中',
  `deploySoftId` varchar(50) DEFAULT NULL COMMENT '部署软件ID集合',
   `breakdownTime` datetime DEFAULT NULL COMMENT '故障时间',
  `breakdownInfo` varchar(500) DEFAULT NULL COMMENT '故障信息',
  `exceptSolveTime` datetime DEFAULT NULL COMMENT '期望解决时间',
  `exceptResult` varchar(200) DEFAULT NULL COMMENT '期望结果',
  `breakdownLevel` varchar(20) DEFAULT NULL COMMENT '故障级别',
   `partitionInfo` varchar(400) DEFAULT NULL COMMENT '分区信息',
    `directory` varchar(100) DEFAULT NULL COMMENT '目录',
  `expTime` datetime DEFAULT NULL COMMENT '过期时间',
  `vipNum` varchar(20) DEFAULT NULL COMMENT 'VIP申请数量',
  `middlewareMsg` varchar(500) DEFAULT NULL COMMENT '申请中间件操作说明',
  `appmsg` varchar(500) DEFAULT NULL COMMENT '维运申请说明',
  `expansionType` varchar(20) DEFAULT NULL COMMENT '扩容类型',
  `expansionSize` varchar(20) DEFAULT NULL COMMENT '扩容大小',
  `remark1` varchar(200) DEFAULT NULL COMMENT '业务备用字段1',
  `remark2` varchar(200) DEFAULT NULL COMMENT '业务备用字段2',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '建创时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


