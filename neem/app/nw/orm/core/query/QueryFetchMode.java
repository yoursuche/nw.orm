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
