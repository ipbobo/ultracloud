DROP table if EXISTS cmp_dict;
CREATE TABLE `cmp_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  `dictType` varchar(30) NOT NULL COMMENT '字典表代码，按照字典实际意义来命名，如proj_stat，表示是项目状态字典表',
  `dictCode` varchar(30) NOT NULL COMMENT '字典项代码，编号规则一般是99项之内用01，02....，超出99项的又是小于999项用001，002.....来命名,以此类推',
  `dictValue` varchar(200) NOT NULL COMMENT '字典值，是字典项代码对应的字典内容，如项目储备（字典项01对应的中文解释），项目立项（字典项02对应的中文解释）',
  `dictDesc` varchar(60) DEFAULT NULL COMMENT '字典描述',
  `dictDefault` varchar(1) DEFAULT '0' COMMENT '是否默认：0-否；1是',
  `dictOrder` int(10) DEFAULT '0' COMMENT '字典顺序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `indx_cmp_dict_dd` (`dictType`,`dictCode`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='字典表';
