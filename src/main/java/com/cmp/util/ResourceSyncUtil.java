package com.cmp.util;

import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.vmware.vim25.mo.Datacenter;

public class ResourceSyncUtil {

	public static PageData initDatacenter(String datacenterId, Datacenter dc, PageData cloudPD) {
		PageData dcPD = new PageData();
		datacenterId = UuidUtil.get32UUID();
		dcPD.put("id", datacenterId);
		dcPD.put("name", dc.getName());
		dcPD.put("uuid", dc.getMOR().get_value());
		dcPD.put("type", "vmware");
		dcPD.put("cpf_id", cloudPD.getString("id"));
		dcPD.put("version", cloudPD.getString("version"));
		
		return dcPD;
	}
}
