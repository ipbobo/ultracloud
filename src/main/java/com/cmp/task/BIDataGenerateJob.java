package com.cmp.task;

import java.util.Map;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cmp.common.Constants;
import com.cmp.service.CmpLogService;
import com.cmp.service.bi.BIDatacenterService;
import com.cmp.service.system.TimeTaskServiceImpl;
import com.fh.controller.base.BaseController;
import com.fh.util.Logger;
import com.fh.util.PageData;

/**
 * quartz BI数据生成定时任务
 * 
 * @author
 * @date
 */
public class BIDataGenerateJob extends BaseController implements Job {

	protected Logger logger = Logger.getLogger(this.getClass());

	@Resource
	private CmpLogService cmpLogService;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		WebApplicationContext webctx = ContextLoader.getCurrentWebApplicationContext();

		try {
			long start = System.currentTimeMillis();
			logger.info("BIDataGenerateJob start...");
			
			cmpLogService = (CmpLogService) webctx.getBean("cmpLogService");
			cmpLogService.addCmpLog(Constants.OPER_TYPE_11, "生成BI计费数据", "开始", Constants.OPER_STATUS_0, "");

			JobDataMap dataMap = context.getJobDetail().getJobDataMap();
			dataMap.get("parameterList");
			                    
			BIDatacenterService biDatacenterService = (BIDatacenterService) webctx.getBean("biDatacenterService");
			biDatacenterService.biDataGenerateHandler();

			long time = System.currentTimeMillis() - start;
			logger.info("BIDataGenerateJob end.");
			cmpLogService.addCmpLog(Constants.OPER_TYPE_11, "生成BI计费数据", "结束. 耗时：" + time, Constants.OPER_STATUS_0, "");
		} catch (Exception e1) {
			logger.error(e1.getMessage());
		}
	}

	/**
	 * 把定时备份任务状态改为关闭
	 * 
	 * @param pd
	 * @param parameter
	 * @param webctx
	 */
	public void shutdownJob(JobExecutionContext context, PageData pd, Map<String, Object> parameter, WebApplicationContext webctx) {
		try {
			context.getScheduler().shutdown(); // 备份异常时关闭任务
			TimeTaskServiceImpl timeTaskService = (TimeTaskServiceImpl) webctx.getBean("timingbackupService");
			pd.put("STATUS", 2); // 改变定时运行状态为2，关闭
			pd.put("TIMINGBACKUP_ID", parameter.get("TIMINGBACKUP_ID").toString()); // 定时备份ID
			timeTaskService.changeStatus(pd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
