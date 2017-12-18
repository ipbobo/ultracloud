package com.cmp.mgr;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cmp.entity.tcc.TccCloudPlatform;
import com.cmp.mgr.impl.PlatformBindedCloudArchManager;

@Component
public class CloudArchManagerAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Lock lock = new ReentrantLock();

	private Map<TccCloudPlatform, CloudArchManager> map = new HashMap<>();

	public CloudArchManager getCloudArchManagerAdaptee(TccCloudPlatform platform) {
		CloudArchManager cloudArchManager = map.get(platform);
		if (cloudArchManager == null) {
			cloudArchManager = registerCloudArchManager(platform);
		}

		return cloudArchManager;
	}

	private CloudArchManager registerCloudArchManager(TccCloudPlatform platform) {
		CloudArchManager cloudArchManager = null;

		lock.lock();
		try {
			if (map.get(platform) == null) {
				return null;
			}

			String qualifiedClassName = platform.getPlatformManagerType();
			if (StringUtils.isBlank(qualifiedClassName)) {
				return null;
			}

			Class<?> clz = Class.forName(qualifiedClassName);
			cloudArchManager = (CloudArchManager) clz.newInstance();
			map.putIfAbsent(platform, cloudArchManager);

			if (cloudArchManager instanceof PlatformBindedCloudArchManager) {
				((PlatformBindedCloudArchManager) cloudArchManager).setPlatform(platform);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			lock.unlock();
		}

		return cloudArchManager;
	}

}
