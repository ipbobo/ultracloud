package com.cmp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpAxis;
import com.cmp.sid.CmpRes;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;

//仪表盘服务
@Service
public class DashboardService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	//虚机总量查询
	public Long getVirNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getVirNum", null);
	}
	
	//宿主机总量查询
	public Long getHostNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getHostNum", null);
	}
	
	//物理机总量查询
	public Long getPhysNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getPhysNum", null);
	}
	
	//用户总数查询
	public Long getUserNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getUserNum", null);
	}
	
	//项目总数查询
	public Long getProjNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getProjNum", null);
	}
	
	//工单总数查询
	public Long getWorkOrderNum() throws Exception {
		return (Long)dao.findForObject("DashboardMapper.getWorkOrderNum", null);
	}
	
	//虚机详细信息查询
	public CmpRes getVirDtl() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setCpuUseNum("1");
		cmpRes.setCpuTotalNum("10");
		cmpRes.setMemUseNum("2");
		cmpRes.setMemTotalNum("20");
		cmpRes.setStoreUseNum("21");
		cmpRes.setStoreTotalNum("100");
		return cmpRes;
	}
	
	//物理机详细信息查询
	public CmpRes getPhysDtl() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setCpuUseNum("1");
		cmpRes.setCpuTotalNum("10");
		cmpRes.setMemUseNum("2");
		cmpRes.setMemTotalNum("20");
		cmpRes.setStoreUseNum("21");
		cmpRes.setStoreTotalNum("100");
		return cmpRes;
	}
	
	//虚拟机负载
	public CmpRes getVirLoad() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setLoadLittleNum("2");
		cmpRes.setLoadMiddleNum("3");
		cmpRes.setLoadHeightNum("4");
		cmpRes.setLoadStopNum("5");
		return cmpRes;
	}
	
	//宿主机负载
	public CmpRes getHostLoad() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setLoadLittleNum("12");
		cmpRes.setLoadMiddleNum("13");
		cmpRes.setLoadHeightNum("14");
		cmpRes.setLoadStopNum("15");
		return cmpRes;
	}
	
	//物理机负载
	public CmpRes getPhysLoad() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setLoadLittleNum("22");
		cmpRes.setLoadMiddleNum("23");
		cmpRes.setLoadHeightNum("24");
		cmpRes.setLoadStopNum("25");
		return cmpRes;
	}
	
	//虚拟机运行
	public CmpRes getVirRun() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setRunRunnigNum("2");
		cmpRes.setRunHangupNum("3");
		cmpRes.setRunCloseNum("4");
		return cmpRes;
	}
	
	//宿主机运行
	public CmpRes getHostRun() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setRunRunnigNum("12");
		cmpRes.setRunHangupNum("13");
		cmpRes.setRunCloseNum("14");
		return cmpRes;
	}
	
	//物理机运行
	public CmpRes getPhysRun() throws Exception {
		CmpRes cmpRes=new CmpRes();
		cmpRes.setRunRunnigNum("22");
		cmpRes.setRunHangupNum("23");
		cmpRes.setRunCloseNum("24");
		return cmpRes;
	}
	
	//CPU资源使用量趋势
	public CmpAxis getCpuResRate(String timeType) throws Exception {
		CmpAxis cmpAxis=new CmpAxis();
		cmpAxis.setXaxis1("12/2");
		cmpAxis.setXaxis2("12/4");
		cmpAxis.setXaxis3("12/5");
		cmpAxis.setXaxis4("12/12");
		cmpAxis.setXaxis5("12/18");
		cmpAxis.setXaxis6("12/22");
		cmpAxis.setYaxis1("0.1");
		cmpAxis.setYaxis2("0.3");
		cmpAxis.setYaxis3("0.5");
		cmpAxis.setYaxis4("0.7");
		cmpAxis.setYaxis5("0.9");
		cmpAxis.setYaxis6("1");
		return cmpAxis;
	}
		
	//内存资源使用量趋势
	public CmpAxis getMemResRate(String timeType) throws Exception {
		CmpAxis cmpAxis=new CmpAxis();
		cmpAxis.setXaxis1("12/1");
		cmpAxis.setXaxis2("12/2");
		cmpAxis.setXaxis3("12/3");
		cmpAxis.setXaxis4("12/4");
		cmpAxis.setXaxis5("12/5");
		cmpAxis.setXaxis6("12/6");
		cmpAxis.setYaxis1("0.2");
		cmpAxis.setYaxis2("0.4");
		cmpAxis.setYaxis3("0.5");
		cmpAxis.setYaxis4("0.6");
		cmpAxis.setYaxis5("0.8");
		cmpAxis.setYaxis6("1");
		return cmpAxis;
	}
	
	//磁盘资源使用量趋势
	public CmpAxis getStoreResRate(String timeType) throws Exception {
		CmpAxis cmpAxis=new CmpAxis();
		cmpAxis.setXaxis1("12/11");
		cmpAxis.setXaxis2("12/12");
		cmpAxis.setXaxis3("12/13");
		cmpAxis.setXaxis4("12/14");
		cmpAxis.setXaxis5("12/15");
		cmpAxis.setXaxis6("12/16");
		cmpAxis.setYaxis1("0.1");
		cmpAxis.setYaxis2("0.5");
		cmpAxis.setYaxis3("0.6");
		cmpAxis.setYaxis4("0.7");
		cmpAxis.setYaxis5("0.9");
		cmpAxis.setYaxis6("1");
		return cmpAxis;
	}
	
	//资源使用列表
	@SuppressWarnings("unchecked")
	public List<PageData> getResUseList(Page page, String resType) throws Exception {
		return (List<PageData>) dao.findForList("BizviewMapper.getCloudHostPageList", page);
	}
}