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