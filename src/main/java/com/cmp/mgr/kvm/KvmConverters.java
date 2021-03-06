package com.cmp.mgr.kvm;

import java.util.function.Function;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.libvirt.Domain;
import org.libvirt.DomainSnapshot;

import com.cmp.entity.tcc.TccVirtualMachine;
import com.cmp.entity.tcc.TccVmSnapshot;

public class KvmConverters {

	public Function<Domain, TccVirtualMachine> toVirtualMachine() {
		return domain -> {
			TccVirtualMachine vm = new TccVirtualMachine();
			try {
				vm.setId(domain.getID());
				vm.setName(domain.getName());
				vm.setOSType(domain.getOSType());
				vm.setVcpus(domain.getMaxVcpus());
				vm.setMemory(Math.round(domain.getMaxMemory() / 1024 / 1024f));
				vm.setXmlDesc(domain.getXMLDesc(0));
				vm.setUUID(domain.getUUIDString());
				vm.setState(domain.getInfo().state.name());
				vm.setActive(0);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return vm;
		};
	}

	public Function<DomainSnapshot, TccVmSnapshot> toSnapshot() {
		return snapshot -> {
			TccVmSnapshot vmsnapshot = new TccVmSnapshot();
			try {
				String xmlDesc = snapshot.getXMLDesc();
				Document xmlDoc = DocumentHelper.parseText(xmlDesc);

				vmsnapshot.setName(xmlDoc.selectSingleNode("//domainsnapshot/name").getText());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			return vmsnapshot;
		};
	}

}
