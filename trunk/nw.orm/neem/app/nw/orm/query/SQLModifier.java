package nw.orm.query;

/**
 * Adds paging and return class attributes to simple sql queries. A call to @see {@link #setPaginated(int, int)}
 * is used to enable pagination
 * @author kulgan
 *
 */
public class SQLModifier {
	
	private boolean paginated;
	private int pageIndex;
	private int maxResult;
	
	private Class<?> queryClass;
	
	public SQLModifier() {
		
	}
	
	public SQLModifier(Class<?> queryClass) {
		this.queryClass = queryClass;
	}
	
	public boolean isPaginated() {
		return paginated;
	}

	/**
	 * enables pagination for associated query
	 * @param pageIndex index where result set should start from.
	 * @param maxResult the maximum number of items to return
	 */
	public void setPaginated(int pageIndex, int maxResult) {
		setMaxResult(maxResult);//maxResult;
		setPageIndex(pageIndex);// = pageIndex;
		this.paginated = true;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	private void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getMaxResult() {
		return maxResult;
	}

	protected void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}
	
	public Class<?> getQueryClazz() {
		return queryClass;
	}

	protected void setQueryClazz(Class<?> returnClazz) {
		this.queryClass = returnClazz;
	}

}
