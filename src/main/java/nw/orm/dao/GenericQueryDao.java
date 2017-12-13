package nw.orm.dao;

import java.io.Serializable;

import java.util.List;

import org.hibernate.criterion.Criterion;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryModifier;
import nw.orm.core.query.QueryParameter;

/**
 * Generic DAO API
 * @author Rowland
 *
 */
public interface GenericQueryDao {
	
	/**
	 * Generic save
	 * @param entity entity to save
	 * @return primary key of the freshly saved entity
	 * 
	 * @throws NwormQueryException
	 */
	Serializable save(Object entity);

	List<Serializable> bulkSave(List<?> items);

	boolean delete(Object entity);

	boolean bulkDelete(List<?> entities);

	boolean update(Object entity);

	boolean bulkUpdate(List<?> entities);

	boolean saveOrUpdate(Object entity);

	<T> T query(Class<T> resultClass, String queryString, QueryParameter ... parameters);

	<T> List<T> queryList(Class<T> resultClass, String hql, QueryParameter ... parameters);

	<T> T get(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria);

	<T> List<T> list(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria);
	

}
