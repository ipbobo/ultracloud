DROP table if EXISTS cmp_sequence;
CREATE TABLE `cmp_sequence` (
  `seqName` varchar(60) NOT NULL COMMENT '序列名称，即表名',
  `currVal` int(11) NOT NULL COMMENT '当前值',
  `incrVal` int(11) default 1 COMMENT '增长值',
  `bitNum` int(11) default 5 COMMENT '位数',
  PRIMARY KEY (`seqName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='序列表';
