package com.cmp.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.sid.CmpOrder;
import com.fh.dao.DaoSupport;

@Service
public class CmpOrderService {
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
	public List<CmpOrder> getBuyHisList(String applyUserId) throws Exception {
		return (List<CmpOrder>)dao.findForList("CmpOrderMapper.getBuyHisList", applyUserId);
	}
	
	//已购历史列表总数查询
	public Long getBuyHisNum(String applyUserId) throws Exception {
		return (Long)dao.findForObject("CmpOrderMapper.getBuyHisNum", applyUserId);
	}
		
	//新增清单或套餐
	public void saveCmpOrder(CmpOrder cmpOrder) throws Exception {
		dao.save("CmpOrderMapper.saveCmpOrder", cmpOrder);
	}
	
	//新增套餐清单
	public void addPckgList(CmpOrder cmpOrder) throws Exception {
		dao.save("CmpOrderMapper.addPckgList", cmpOrder);
	}
	
	//更新清单状态
	public void updateCmpOrderStatus(String orderNo) throws Exception {
		dao.update("CmpOrderMapper.updateCmpOrderStatus", orderNo);
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
}