package com.cmp.mgr.kvm;

import java.util.Arrays;
import java.util.function.Function;

import org.libvirt.Domain;

import com.cmp.entity.tcc.TccVirtualMachine;

public class KvmConverters {

	public Function<Domain, TccVirtualMachine> toVirtualMachine() {
		return domain -> {
			TccVirtualMachine vm = new TccVirtualMachine();
			try {
				vm.setId(domain.getID());
				vm.setName(domain.getName());
				vm.setOSType(domain.getOSType());
				vm.setMaxVcpus(domain.getMaxVcpus());
				vm.setXmlDesc(domain.getXMLDesc(0));
				vm.setUUID(domain.getUUIDString());
				vm.setDomainInfo(domain.getInfo().toString());
				vm.setMemoryStatistic((Arrays.asList(domain.memoryStats(8))).toString());
				vm.setCpuInfo((Arrays.asList(domain.getVcpusInfo())).toString());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return vm;
		};
	}

}
