DROP FUNCTION IF EXISTS `func_nextval`;
CREATE FUNCTION `func_nextval`(i_seqName varchar(60), i_prefix varchar(5))
RETURNS varchar(20)
BEGIN
	DECLARE v_count int(11);
	select count(1) into v_count from cmp_sequence where seqName=i_seqName;
	if v_count=0 THEN
		insert into cmp_sequence(seqName, currVal, incrVal) value(i_seqName, 1, 1);
	else
		update cmp_sequence set currval=currval+incrVal where seqName=i_seqName;
	end if;
	
	return func_currVal(i_seqName, i_prefix);
END