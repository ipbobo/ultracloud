package com.cmp.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmp.service.StorageService;
import com.cmp.service.resourcemgt.CloudplatformService;
import com.cmp.service.resourcemgt.ClusterService;
import com.cmp.service.resourcemgt.DatacenterService;
import com.cmp.service.resourcemgt.DatacenternetworkService;
import com.cmp.service.resourcemgt.HostmachineService;
import com.fh.controller.base.BaseController;

/**
 * 资源管理 控制层
 * 
 * @author liuweixing
 *
 */
@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController {
	@Resource(name = "cloudplatformService")
	private CloudplatformService cloudplatformService;

	@Resource(name = "datacenterService")
	private DatacenterService datacenterService;

	@Resource(name = "clusterService")
	private ClusterService clusterService;

	@Resource(name = "hostmachineService")
	private HostmachineService hostmachineService;

	@Resource(name = "storageService")
	private StorageService storageService;

	@Resource(name = "datacenternetworkService")
	private DatacenternetworkService datacenternetworkService;

}
