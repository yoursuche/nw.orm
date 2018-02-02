package nw.orm.jpa;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;
import nw.orm.dao.Dao;
import nw.orm.dao.Paging;

public interface JDao<T> extends Dao<T> {
	
	/**
	 * No pagination support, just lists all entries that meets
	 * criteria
	 * @param parameters Zero or more query parameters
	 * @return list of results
	 * @throws NwormQueryException Query Exception
	 */
	List<T> list(QueryParameter ... parameters);
	
	/**
	 * Supports paging
	 * @param paging {@link Paging} 
	 * @param parameters Zero or more parameters
	 * @return list of entity instances
	 * @throws NwormQueryException Query Exception
	 */
	List<T> list(Paging paging, QueryParameter ... parameters);

	CriteriaBuilder getCriteriaBuilder();

	T get(QueryParameter ... parameters);

}
