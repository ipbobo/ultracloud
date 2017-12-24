package com.cmp.mgr.vmware;

import java.util.function.Function;

import com.cmp.entity.tcc.TccVirtualMachine;
import com.vmware.vim25.mo.VirtualMachine;

public class VMWareConvertors {

	public static Function<VirtualMachine, TccVirtualMachine> vmConverter() {
		return vm -> {
			TccVirtualMachine tccVm = new TccVirtualMachine();
			tccVm.setUUID(vm.getMOR().getVal());
			tccVm.setName(vm.getName());

			return tccVm;
		};
	}

}
