DROP table if EXISTS cmp_softparam;
CREATE TABLE `cmp_softparam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `softCode` varchar(50) DEFAULT NULL COMMENT '软件代码，多个用英文逗号分隔',
  `softParam` varchar(200) DEFAULT NULL COMMENT '软件参数，多个用英文逗号分隔',
  `orderNo` varchar(20) NOT NULL COMMENT '清单编号：O+YYYYMMDD+00001',
  PRIMARY KEY (`id`),
  UNIQUE KEY `indx_cmp_softparam_os` (`orderNo`,`softCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='软件参数表';