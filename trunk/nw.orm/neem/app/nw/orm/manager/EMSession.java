package nw.orm.manager;

import java.util.concurrent.ConcurrentHashMap;


abstract class EMSession {

	private static ConcurrentHashMap<String, REntityManager> activeManagers = new ConcurrentHashMap<String, REntityManager>();

	static REntityManager getManager(String configFile) {
		return activeManagers.get(configFile);
	}

	protected static void putManager(String file, REntityManager manager) {
		EMSession.activeManagers.put(file, manager);
	}


}
