package nw.orm.core.service;

import java.util.concurrent.ConcurrentHashMap;

// TODO: Auto-generated Javadoc
/**
 * In memory store for in use Database services.
 *
 * @author Ogwara O. Rowland
 */
public abstract class NwormFactory {

	/** The active managers. */
	private static ConcurrentHashMap<String, IService> activeManagers = new ConcurrentHashMap<String, IService>();

	/**
	 * get a data service manager by its reference.
	 *
	 * @param configFile the config file
	 * @return the manager
	 */
	public static IService getManager(String configFile) {
		return activeManagers.get(configFile);
	}

	/**
	 * Adds a new service by reference.
	 *
	 * @param file refernce key
	 * @param manager reference service
	 */
	public static void putManager(String file, IService manager) {
		NwormFactory.activeManagers.put(file, manager);
	}


}
