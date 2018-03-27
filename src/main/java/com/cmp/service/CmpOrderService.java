package com.cmp.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cmp.sid.CmpOrder;
import com.cmp.sid.CmpPrice;
import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service
public class CmpOrderService {
	private static Logger logger = Logger.getLogger(CmpOrderService.class);
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Resource
	private CmpDictService cmpDictService;

	//清单详细信息查询
	@SuppressWarnings("unchecked")
	public List<CmpOrder> getOrderDtl(String orderNo) throws Exception {
		return (List<CmpOrder>)dao.findForList("CmpOrderMapper.getOrderDtl", orderNo);
	}
	
	//套餐列表查询
	@SuppressWarnings("unchecked")
	public List<CmpOrder> getPckgList(String applyUserId) throws Exception {
		List<CmpOrder> list=(List<CmpOrder>)dao.findForList("CmpOrderMapper.getPckgList", applyUserId);
		if(list!=null && !list.isEmpty()){
			for(CmpOrder co: list){
				Map<String, String> diskTypeMap=cmpDictService.getCmpDictMap("disk_type");
				String[] diskTypes=co.getDiskType().split(",");
				if(diskTypes!=null && diskTypes.length>0){
					for(String diskType: diskTypes){
						co.setDiskTypeMap(diskType, diskTypeMap.get(diskType));
					}
				}
			}
		}
		
		return list;
	}
	
	//购物车列表查询
	@SuppressWarnings("unchecked")
	public List<CmpOrder> getShoppingCartList(String applyUserId) throws Exception {
		return (List<CmpOrder>)dao.findForList("CmpOrderMapper.getShoppingCartList", applyUserId);
	}
	
	//购物车列表总数查询
	public Long getShoppingCartNum(String applyUserId) throws Exception {
		return (Long)dao.findForObject("CmpOrderMapper.getShoppingCartNum", applyUserId);
	}
	
	//已购历史列表查询
	@SuppressWarnings("unchecked")
	public List<CmpOrder> getBuyHisList(PageData pd) throws Exception {
		return (List<CmpOrder>)dao.findForList("CmpOrderMapper.getBuyHisList", pd);
	}
	
	//已购历史列表总数查询
	public Long getBuyHisNum(String applyUserId) throws Exception {
		return (Long)dao.findForObject("CmpOrderMapper.getBuyHisNum", applyUserId);
	}
		
	//新增清单或套餐
	public void saveCmpOrder(CmpOrder cmpOrder) throws Exception {
		dao.save("CmpOrderMapper.saveCmpOrder", cmpOrder);
	}
	
	//软件参数列表新增
	public void saveSoftParams(String orderNo, String softCodeStr, String softParamStr) throws Exception {
		if(!StringUtils.isBlank(softParamStr)){
			String[] softCodes=softCodeStr.split(",", -1);//软件代码：5,6
			String[] softParams=softParamStr.split("\\|", -1);//软件参数：path:/tomcat,user:admin,passwd:admin|path:/tomcat,user:admin,passwd:admin
			for(int i=0;i<softCodes.length;i++){
				if(!StringUtils.isBlank(softParams[i])){
					saveSoftParam(orderNo, softCodes[i], softParams[i]);//软件参数新增
				}
			}
		}
	}
	
	//软件参数新增
	private void saveSoftParam(String orderNo, String softCode, String softParamStr) throws Exception {
		String[] softParams=softParamStr.split(",", -1);//软件参数：path:/tomcat,user:admin,passwd:admin
		for(int i=0;i<softParams.length;i++){
			String[] params=softParams[i].split("\\:", -1);//user:admin
			PageData pd=new PageData("orderNo", orderNo, "softCode", softCode, "paramKey", params[0], "paramValue", params[1]);
			dao.save("CmpOrderMapper.saveSoftParam", pd);
		}
	}
	
	//新增套餐清单
	public void addPckgList(CmpOrder cmpOrder) throws Exception {
		dao.save("CmpOrderMapper.addPckgList", cmpOrder);
	}
	
	//更新清单状态
	public void updateCmpOrderStatus(PageData pd) throws Exception {
		dao.update("CmpOrderMapper.updateCmpOrderStatus", pd);
	}
	
	//清空购物车
	public void clearShoppingCart(String applyUserId) throws Exception {
		dao.delete("CmpOrderMapper.clearShoppingCart", applyUserId);
	}
	
	//删除清单
	public void delCmpOrder(String orderNo) throws Exception {
		dao.delete("CmpOrderMapper.delCmpOrder", orderNo);
	}
	
	//删除套餐
	public void delPckg(String pckgId) throws Exception {
		dao.delete("CmpOrderMapper.delPckg", pckgId);
	}
	
	//计算价格查询
	public CmpPrice getCmpPrice() throws Exception {
		String price=(String)dao.findForObject("CmpOrderMapper.getCmpPrice", null);
		String[] prices=null;
		if(price==null || "".equals(price) || (prices=price.split(","))==null || prices.length!=3){
			logger.error("CPU、内存、磁盘计算价格不能为空");
			return null;
		}
		
		CmpPrice cmpPrice=new CmpPrice();
		cmpPrice.setCpuPrice(prices[0]);//cpu
		cmpPrice.setMemPrice(prices[2]);//内存
		cmpPrice.setStorePrice(prices[1]);//磁盘
		
		return cmpPrice;
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> selectByCurrentDate() throws Exception {
		return (List<PageData>)dao.findForList("CmpOrderMapper.selectByCurrentDate", null);
	}
}