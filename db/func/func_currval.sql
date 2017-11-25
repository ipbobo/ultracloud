DROP FUNCTION IF EXISTS `func_currval`;
CREATE FUNCTION `func_currval`(i_seqName varchar(60))
RETURNS varchar(20)
BEGIN
	DECLARE v_retVal varchar(20);
	select concat(prefix, date_format(now(), '%Y%m%d'), lpad(currVal, bitNum, '0')) into v_retVal from cmp_sequence where seqName=i_seqName;
	RETURN v_retVal;
END