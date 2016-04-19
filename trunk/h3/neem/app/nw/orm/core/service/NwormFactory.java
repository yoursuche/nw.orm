/* 
 * Copyright 2013 - 2015, Neemworks Nigeria <nw.orm@nimworks.com>
 Permission to use, copy, modify, and distribute this software for any
 purpose with or without fee is hereby granted, provided that the above
 copyright notice and this permission notice appear in all copies.

 THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
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
	private static ConcurrentHashMap<String, NwormService> activeManagers = new ConcurrentHashMap<String, NwormService>();

	/**
	 * get a data service manager by its reference.
	 *
	 * @param configFile the config file
	 * @return the manager
	 */
	public static NwormService getManager(String configFile) {
		return activeManagers.get(configFile);
	}

	/**
	 * Adds a new service by reference.
	 *
	 * @param file refernce key
	 * @param manager reference service
	 */
	public static void putManager(String file, NwormService manager) {
		NwormFactory.activeManagers.put(file, manager);
	}


}
