package com.cmp.mgr.openstack;

import java.util.function.Function;

import org.openstack4j.model.compute.Server;

import com.cmp.entity.tcc.TccVirtualMachine;

public class OpenstackConverters {

	public Function<Server, TccVirtualMachine> toVirtualMachine() {
		return svr -> {
			TccVirtualMachine vm = new TccVirtualMachine();
			try {
				vm.setId(Integer.valueOf(svr.getId()));
				vm.setName(svr.getName());
				// vm.setOSType();
				// vm.setMaxVcpus(svr.getMaxVcpus());
				// vm.setXmlDesc(svr.getXMLDesc(0));
				// vm.setUUID(svr.getUUIDString());
				// vm.setDomainInfo(svr.getInfo().toString());
				// vm.setMemoryStatistic((Arrays.asList(svr.memoryStats(8))).toString());
				// vm.setCpuInfo((Arrays.asList(svr.getVcpusInfo())).toString());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return vm;
		};
	}

}
