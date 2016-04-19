/*
 * Property of Neemworks Nigeria 
 * Copyright 2013 - 2015, all rights reserved
 */
package nw.orm.core.query;

import org.hibernate.FetchMode;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryFetchMode.
 */
public class QueryFetchMode {
	
	/** The alias. */
	private String alias;
	
	/** The fetch mode. */
	private FetchMode fetchMode = FetchMode.DEFAULT;

	/**
	 * Gets the alias.
	 *
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias.
	 *
	 * @param alias the new alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the fetch mode.
	 *
	 * @return the fetch mode
	 */
	public FetchMode getFetchMode() {
		return fetchMode;
	}

	/**
	 * Sets the fetch mode.
	 *
	 * @param fetchMode the new fetch mode
	 */
	public void setFetchMode(FetchMode fetchMode) {
		this.fetchMode = fetchMode;
	}

}
