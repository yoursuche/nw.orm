package nw.orm.dao;

import java.io.Serializable;
import java.util.List;

import nw.orm.core.exception.NwormQueryException;
import nw.orm.core.query.QueryParameter;

public interface GenericQueryDao {
	
	/**
	 * 
	 * @param item
	 * @return primary key
	 * 
	 * @throws NwormQueryException
	 */
	Serializable save(Object item);

	List<Serializable> bulkSave(List<?> items);

	boolean delete(Object entity);

	boolean bulkDelete(List<?> entities);

	boolean update(Object entity);

	boolean bulkUpdate(List<?> entities);

	boolean saveOrUpdate(Object entity);
	
	T getByQuery(String query, QueryParameter... parameters);

}
