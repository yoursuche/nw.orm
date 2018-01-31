package nw.orm.hibernate;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.dao.Dao;
import nw.orm.dao.Paging;

public interface HDao<T> extends Dao<T> {
	
	/**
	 * No pagination support, just lists all entries that meets
	 * criteria
	 * @param criteria criteria Zero or more Restrictions
	 * @return lists of entities matching criteria
	 * @throws NwormQueryException
	 */
	List<T> list(Criterion ... criteria);
	
	/**
	 * Supports paging
	 * @param paging {@link Paging} 
	 * @param criteria Zero or more Restrictions
	 * @return lists of entities matching criteria
	 * @throws NwormQueryException
	 */
	List<T> list(Paging paging, Criterion ... criteria);
	
	/**
	 * 
	 * @param criteria
	 * @return
	 * @throws NwormQueryException
	 */
	T get(Criterion ... criteria);
	
	List<T> getByExample(Example example);
	
}
