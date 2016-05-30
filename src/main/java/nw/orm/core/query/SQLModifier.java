/*
 * Property of Neemworks Nigeria
 * Copyright 2013 - 2015, all rights reserved
 */
package nw.orm.core.query;

/**
 * Adds paging and return class attributes to simple sql queries. A call to @see {@link #setPaginated(int, int)}
 * is used to enable pagination
 * @author kulgan
 *
 */
public class SQLModifier {

	/** The paginated. */
	private boolean paginated;

	/** The page index. */
	private int pageIndex;

	/** The max result. */
	private int maxResult;

	/** The query class. */
	private Class<?> queryClass;

	/**
	 * Instantiates a new SQL modifier.
	 */
	public SQLModifier() {

	}

	/**
	 * Instantiates a new SQL modifier.
	 *
	 * @param queryClass the query class
	 */
	public SQLModifier(Class<?> queryClass) {
		this.queryClass = queryClass;
	}

	/**
	 * Checks if is paginated.
	 *
	 * @return true, if is paginated
	 */
	public boolean isPaginated() {
		return paginated;
	}

	/**
	 * enables pagination for associated query.
	 *
	 * @param pageIndex index where result set should start from.
	 * @param maxResult the maximum number of items to return
	 */
	public void setPaginated(int pageIndex, int maxResult) {
		setMaxResult(maxResult);//maxResult;
		setPageIndex(pageIndex);// = pageIndex;
		this.paginated = true;
	}

	/**
	 * Gets the page index.
	 *
	 * @return the page index
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * Sets the page index.
	 *
	 * @param pageIndex the new page index
	 */
	private void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * Gets the max result.
	 *
	 * @return the max result
	 */
	public int getMaxResult() {
		return maxResult;
	}

	/**
	 * Sets the max result.
	 *
	 * @param maxResult the new max result
	 */
	protected void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	/**
	 * Gets the query clazz.
	 *
	 * @return the query clazz
	 */
	public Class<?> getQueryClazz() {
		return queryClass;
	}

	/**
	 * Sets the query clazz.
	 *
	 * @param returnClazz the new query clazz
	 */
	protected void setQueryClazz(Class<?> returnClazz) {
		this.queryClass = returnClazz;
	}

}
