package nw.orm.query;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

/**
 * Adds extra parameters to criteria operation
 * @see {@link SQLModifier}
 * @author kulgan
 *
 */
public class QueryModifier<T> extends SQLModifier<T>{
	
	private boolean transformResult;

	private List<Order> orderBys = new ArrayList<Order>();
	private List<QueryAlias> aliases = new ArrayList<QueryAlias>();
	private List<Projection> projections = new ArrayList<Projection>();
	
	/**
	 * 
	 * @param queryClass class used for creating criteria
	 */
	public QueryModifier(Class<T> queryClass) {
		super(queryClass);
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

	public List<Projection> getProjections() {
		return projections;
	}

	public boolean isTransformResult() {
		return transformResult;
	}

	public void setTransformResult(boolean transformResult) {
		this.transformResult = transformResult;
	}

}
