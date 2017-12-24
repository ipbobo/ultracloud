package com.cmp.mgr;

import org.apache.commons.lang3.StringUtils;

public interface CloudArchTest {

	default void execute(String flag, Runnable runnable) {
		String info = String.format("== Action: %s ", flag);
		info = StringUtils.rightPad(info, 50, "=");

		System.err.println(info);
		try {
			runnable.run();
			Thread.sleep(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

}
