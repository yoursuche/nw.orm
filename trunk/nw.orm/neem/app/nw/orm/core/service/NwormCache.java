package nw.orm.core.service;

import java.util.concurrent.ConcurrentHashMap;


public abstract class NwormCache {

	private static ConcurrentHashMap<String, NwormService> activeManagers = new ConcurrentHashMap<String, NwormService>();

	public static NwormService getManager(String configFile) {
		return activeManagers.get(configFile);
	}

	public static void putManager(String file, NwormService manager) {
		NwormCache.activeManagers.put(file, manager);
	}


}
