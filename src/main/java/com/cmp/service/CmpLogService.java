package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpLog;
import com.cmp.util.StringUtil;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

@Service
public class CmpLogService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//日志列表分页查询
	@SuppressWarnings("unchecked")
	public List<PageData> getLogPageList(Page page) throws Exception{
		return (List<PageData>)dao.findForList("CmpLogMapper.getLogPageList", page);
	}
	
	//新增日志
	public void addCmpLog(String operType, String optObj, String detail, String optStatus, String requestIp) throws Exception {
		CmpLog cmpLog=new CmpLog();
		cmpLog.setUserName(StringUtil.getUserName());//操作者
		cmpLog.setType(operType);//操作类型：1-新增；2-修改；3-删除
		cmpLog.setOptObject(optObj);//操作对象
		cmpLog.setDetail(detail);//操作详细描述
		cmpLog.setOptStatus(optStatus);//操作状态：0-成功；-1-失败
		cmpLog.setIp(requestIp);//操作者ip
		dao.save("CmpLogMapper.addCmpLog", cmpLog);
	}
	
	//删除日志
	public void delCmpLog(String ids) throws Exception {
		dao.delete("CmpLogMapper.delCmpLog", ids);
	}
}