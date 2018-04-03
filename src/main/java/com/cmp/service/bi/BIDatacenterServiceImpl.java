package com.cmp.service.bi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cmp.entity.BiBillDay;
import com.cmp.service.MediumService;
import com.cmp.service.StorageService;
import com.cmp.service.resourcemgt.ClusterService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.resourcemgt.DatacenternetworkService;
import com.cmp.service.resourcemgt.HostmachineService;
import com.cmp.service.resourcemgt.VirtualMachineSyncService;
import com.cmp.service.system.ProductService;
import com.fh.dao.DaoSupport;
import com.fh.util.DateUtil;
import com.fh.util.Logger;
import com.fh.util.PageData;

/**
 * BI数据生成 业务层实现类
 * 
 * @version
 */
@Service("biDatacenterService")
public class BIDatacenterServiceImpl implements BIDatacenterService {
	protected Logger logger = Logger.getLogger(this.getClass());

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name = "datacenterService")
	private DatacenterService datacenterService;

	@Resource(name = "clusterService")
	private ClusterService clusterService;

	@Resource(name = "hostmachineService")
	private HostmachineService hostmachineService;

	@Resource(name = "virtualMachineSyncService")
	private VirtualMachineSyncService virtualMachineSyncService;

	@Resource(name = "storageService")
	private StorageService storageService;

	@Resource(name = "datacenternetworkService")
	private DatacenternetworkService datacenternetworkService;

	@Resource(name = "biBillDayService")
	private BiBillDayService biBillDayService;

	@Resource(name = "biBillMonthService")
	private BiBillMonthService biBillMonthService;

	@Resource(name = "biSoftwareBillService")
	private BiSoftwareBillService biSoftwareBillService;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "mediumService")
	private MediumService mediumService;

	/**
	 * 按天查询虚拟机费用列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllBillDay(PageData pd) throws Exception {
		return biBillDayService.listAll(pd);
	}

	/**
	 * 按月查询虚拟机费用列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAllBillMonth(PageData pd) throws Exception {
		return biBillMonthService.listAll(pd);
	}

	/**
	 * BI数据生成
	 */
	@Override
	public void biDataGenerateHandler() throws Exception {
		List<PageData> vVirtualList = null;

		try {
			// 生成前一天的计费数据
			vVirtualList = this.generateBillDayData();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			// 生成当月的计费数据
			this.generateBillDayMonth();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 生成软件台帐数据
		try {
			this.generateSoftwareBillData(vVirtualList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成按天的计费数据
	 * 
	 * @throws Exception
	 */
	private List<PageData> generateBillDayData() throws Exception {
		PageData pd = new PageData();
		Map<String, Integer> billMap = new HashMap<String, Integer>();

		// 1、先加载计费策略
		List<PageData> productList = productService.listAll(pd);
		for (PageData productPD : productList) {
			billMap.put(productPD.getString("type"), (Integer) productPD.get("price_cur"));
		}

		// 2、再查询虚拟机数据视图
		List<PageData> vVirtualList = biBillDayService.listVBiVirtualBill(pd);

		// 3、计算当天的费用情况
		for (PageData vVirtualPD : vVirtualList) {
			Integer cpu = (Integer) vVirtualPD.get("cpu");
			Long memory = (Long) vVirtualPD.get("memory");
			Long datadisk = (Long) vVirtualPD.get("datadisk");

			Long bill = 0l;
			if (null != cpu) {
				bill += cpu * billMap.get("cpu");
			} else if (null != memory) {
				bill += memory / 1024 / 1024 * billMap.get("memory");
			} else if (null != datadisk) {
				bill += datadisk / 1024 / 1024 * billMap.get("disk");
			}

			vVirtualPD.put("account", bill);
			vVirtualPD.put("date", DateUtil.getDay());
		}

		// 4、删除当天统计数据，防止重复统计
		PageData dateePD = new PageData();
		dateePD.put("date", DateUtil.getDay());
		biBillDayService.deleteBatch(dateePD);

		// 5、当天虚拟机费用数据入库
		if (null != vVirtualList) {
			List<BiBillDay> list = new ArrayList<BiBillDay>();
			for (PageData vVirtualPD : vVirtualList) {
				BiBillDay biBillDay = new BiBillDay();
				biBillDay.setAccount((Long) vVirtualPD.get("account"));
				biBillDay.setCpu((Integer) vVirtualPD.get("cpu"));
				biBillDay.setDatadisk((Integer) vVirtualPD.get("datadisk"));
				biBillDay.setDate(vVirtualPD.getString("date"));
				biBillDay.setDEPARTMENT_ID(vVirtualPD.getString("DEPARTMENT_ID"));
				biBillDay.setDEPARTMENT_NAME(vVirtualPD.getString("DEPARTMENT_NAME"));
				biBillDay.setMemory((Long) vVirtualPD.get("memory"));
				biBillDay.setProject_id(vVirtualPD.getString("project_id"));
				biBillDay.setProject_name(vVirtualPD.getString("project_name"));
				biBillDay.setType(vVirtualPD.getString("type"));
				biBillDay.setUser(vVirtualPD.getString("user"));
				biBillDay.setVm_id((BigInteger) vVirtualPD.get("vm_id"));
				biBillDay.setVm_name(vVirtualPD.getString("vm_name"));

				list.add(biBillDay);
			}
			biBillDayService.saveBatch(list);
		}

		return vVirtualList;
	}

	/**
	 * 生成按月的计费数据
	 * 
	 * @throws Exception
	 */
	private void generateBillDayMonth() throws Exception {
		PageData pd = new PageData();
		String month = DateUtil.getMonth();
		pd.put("month", month);

		// 1、先查询出bi_bill_day表中当月的所有费用
		List<PageData> biBillDayList = biBillDayService.listAll(pd);

		// 2、累计当月费用
		Map<BigInteger, PageData> map = new HashMap<BigInteger, PageData>();
		for (PageData biBillDay : biBillDayList) {
			BigInteger vm_id = (BigInteger) biBillDay.get("vm_id");
			PageData monthPD = map.get(vm_id);
			if (null == monthPD) {
				biBillDay.put("month", month);
				map.put(vm_id, biBillDay);
			} else {
				Long account = (Long) monthPD.get("account") + (Long) biBillDay.get("account");
				monthPD.put("month", month);
				monthPD.put("account", account);
				map.put(vm_id, monthPD);
			}
		}

		// 3、删除当月旧数据
		biBillMonthService.delete(pd);

		// 4、插入当月新数据
		for (Map.Entry<BigInteger, PageData> entry : map.entrySet()) {
			biBillMonthService.save(entry.getValue());
		}
	}

	/**
	 * 生成软件台帐数据
	 * 
	 * @throws Exception
	 */
	private void generateSoftwareBillData(List<PageData> vVirtualList) throws Exception {
		// 1、查询所有的软件列表
		PageData pd = new PageData();
		List<PageData> vSoftwareBillList = biSoftwareBillService.listVSoftwareBill(pd);

		// 2、删除当天统计数据，防止重复统计
		PageData dateePD = new PageData();
		dateePD.put("date", DateUtil.getDay());
		biSoftwareBillService.delete(dateePD);

		// 3、软件台帐数据入库
		for (PageData softwareBillPD : vSoftwareBillList) {
			softwareBillPD.put("date", DateUtil.getDay());
			try {
				biSoftwareBillService.save(softwareBillPD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 计费表报列表
	 */
	@Override
	public List<PageData> listBillBIData(PageData pd) throws Exception {
		return biBillDayService.listBillGroupByVirtualId(pd);
	}

	/**
	 * 资源使用表报列表
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listResourceBIData(PageData pd) throws Exception {
		return biBillDayService.listResourceGroupByVirtualId(pd);
	}

	@Override
	public List<PageData> listSoftwareBIData(PageData pd) throws Exception {
		return biSoftwareBillService.listAllSoftwareBIData(pd);
	}
}
