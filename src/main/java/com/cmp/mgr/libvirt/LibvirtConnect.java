package com.cmp.mgr.libvirt;

import org.libvirt.LibvirtException;

public class LibvirtConnect extends org.libvirt.Connect {

	public LibvirtConnect(String uri, boolean readOnly) throws LibvirtException {
		super(uri, readOnly);
		this.VCP = null;
	}

}
