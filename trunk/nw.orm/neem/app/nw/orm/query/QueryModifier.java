package nw.orm.query;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

public class QueryModifier {

	private boolean paginated;
	private int pageIndex;
	private int maxResult;
	
	private Class<?> queryClass;

	private List<Order> orderBys = new ArrayList<Order>();
	private List<QueryAlias> aliases = new ArrayList<QueryAlias>();
	private List<Projection> projections = new ArrayList<Projection>();
	
	/**
	 * 
	 * @param queryClass class used for creating criteria
	 */
	public QueryModifier(Class<?> queryClass) {
		setQueryClazz(queryClass);
	}
	
	public QueryModifier(){
		
	}

	public List<Order> getOrderBys() {
		return orderBys;
	}

	public void addOrderBy(Order orderBy) {
		this.orderBys.add(orderBy);
	}

	public List<QueryAlias> getAliases() {
		return aliases;
	}

	public void addAlias(QueryAlias alias) {
		aliases.add(alias);// aliases;
	}
	
	public void addProjection(Projection proj){
		projections.add(proj);
	}

	public boolean isPaginated() {
		return paginated;
	}

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

	private void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public List<Projection> getProjections() {
		return projections;
	}

	public Class<?> getQueryClazz() {
		return queryClass;
	}

	private void setQueryClazz(Class<?> returnClazz) {
		this.queryClass = returnClazz;
	}

}
