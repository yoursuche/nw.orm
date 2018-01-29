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
	 * @param criteria criteria Zero or more Restrictions
	 * @return lists of entities matching criteria
	 * @throws NwormQueryException
	 */
	List<T> list(QueryParameter ... parameters);
	
	/**
	 * Supports paging
	 * @param paging {@link Paging} 
	 * @param criteria Zero or more Restrictions
	 * @return lists of entities matching criteria
	 * @throws NwormQueryException
	 */
	List<T> list(Paging paging, QueryParameter ... parameters);

	CriteriaBuilder getCriteriaBuilder();

}
